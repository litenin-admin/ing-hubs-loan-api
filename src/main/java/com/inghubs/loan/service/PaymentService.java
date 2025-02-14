package com.inghubs.loan.service;

import com.inghubs.loan.domain.PaymentDetailDto;
import com.inghubs.loan.domain.PaymentRequestDto;
import com.inghubs.loan.error.exception.*;
import com.inghubs.loan.mappers.LoanInstallmentsMapper;
import com.inghubs.loan.model.Loan;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.repository.LoanRepository;
import com.inghubs.loan.rules.PaymentValidator;
import com.inghubs.loan.service.strategy.EarlyPaymentStrategy;
import com.inghubs.loan.service.strategy.LatePaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.inghubs.loan.utils.Dates.getDueRangeEnd;
import static com.inghubs.loan.utils.Dates.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final LoanRepository loanRepository;
    private final CustomerService customerService;
    private final PaymentValidator paymentValidator;
    private final LatePaymentStrategy latePaymentStrategy;
    private final EarlyPaymentStrategy earlyPaymentStrategy;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PaymentDetailDto payLoan(PaymentRequestDto source) {

        Loan loan = getLoanById(source.loanId());
        if (loan.isPaid()) {
            throw new LoanAlreadyPaidException(
                    "Loan ID: " + source.loanId() + " has already been fully paid" +
                            ". No further payments are needed.");
        }

        // Retrieves the eligible installments for the loan
        List<LoanInstallment> installments = getTargetInstallments(loan, source.amount());

        // Validate the payment amount against the installment rules
        paymentValidator.evaluate(installments, source.amount());

        List<LoanInstallment> paidInstallments = new ArrayList<>();

        for (LoanInstallment target : installments) {
            // Depending on whether the payment is early or late, apply the relevant strategy
            if (now().isBefore(target.getDueDate())) {
                earlyPaymentStrategy.processPayment(target);
            } else {
                latePaymentStrategy.processPayment(target);
            }
            // Decrease the customer's (*used*) credit limit by the paid amount
            customerService.decreaseUsedCreditLimit(source.customerGuid(), target.getPaidAmount());

            // Add the installment to the paid list
            paidInstallments.add(target);
        }

        // If the loan is fully paid, mark it as completed
        if (checkIfLoanFullyPaid(loan)) {
            loan.setPaid(true);
        }

        loanRepository.save(loan);

        return new PaymentDetailDto(
                getDescription(loan, paidInstallments),
                LoanInstallmentsMapper.INSTANCE.toDtoList(paidInstallments));
    }

    public int getPayableCount(BigDecimal amount, BigDecimal installmentAmount) {
        return amount.divide(installmentAmount, RoundingMode.DOWN).intValue();
    }

    private List<LoanInstallment> getTargetInstallments(Loan loan, BigDecimal amount) {

        // Determine the number of installments that can be paid with the given amount
        int payableCount = getPayableCount(amount, loan.getInstallments().getFirst().getAmount());

        var processTime = now();
        var dateRangeEnd = getDueRangeEnd(processTime);

        var targetInstallments = loan.getInstallments()
                .stream()
                .filter(installment ->
                        !installment.isPaid()
                                // Check if it falls in range
                                && isInstallmentInRange(installment, dateRangeEnd))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(LoanInstallment::getDueDate))));

        if (CollectionUtils.isEmpty(targetInstallments)) {
            throw new InstallmentsNotFoundException("No installments found for loan ID: " + loan.getId());
        }

        return targetInstallments.stream()
                .limit(payableCount)
                .toList();
    }

    private boolean isInstallmentInRange(LoanInstallment installment, LocalDateTime end) {
        return installment.getDueDate().isBefore(end);
    }

    private boolean checkIfLoanFullyPaid(Loan loan) {
        return loan.getInstallments().stream().allMatch(LoanInstallment::isPaid);
    }

    private Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    log.warn("Loan not found with ID: {}", loanId);
                    return new LoanNotFoundException("Loan not found with id " + loanId);
                });
    }

    private String getDescription(Loan loan, List<LoanInstallment> paidInstallments) {
        var description = "Paid " + paidInstallments.size() + " installments. " +
                "Total Paid: " + calculateTotalPaidAmount(paidInstallments) + ".";
        description += loan.isPaid() ? " The loan has been fully paid." : " The loan is not yet fully paid.";

        return description;
    }

    public BigDecimal calculateTotalPaidAmount(List<LoanInstallment> installments) {
        return installments.stream()
                .map(LoanInstallment::getPaidAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
