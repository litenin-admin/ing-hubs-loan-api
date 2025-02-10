package com.inghubs.loan.service;

import com.inghubs.loan.domain.LoanRequestDto;
import com.inghubs.loan.error.exception.LoanNotFoundException;
import com.inghubs.loan.model.Customer;
import com.inghubs.loan.model.Loan;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.repository.LoanRepository;
import com.inghubs.loan.repository.LoanInstallmentRepository;
import com.inghubs.loan.rules.InstallmentCountValidator;
import com.inghubs.loan.rules.InterestRateValidator;
import com.inghubs.loan.service.strategy.LoanCalculationStrategy;
import com.inghubs.loan.rules.CreditLimitValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

import static com.inghubs.loan.utils.Dates.now;
import static com.inghubs.loan.utils.Dates.getInstallmentDueDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanManagementService {

    private final CreditLimitValidator creditLimitValidator;
    private final InterestRateValidator interestRateValidator;
    private final InstallmentCountValidator installmentCountValidator;

    private final LoanCalculationStrategy calculationStrategy;

    private final CustomerService customerService;

    private final LoanRepository loanRepository;
    private final LoanInstallmentRepository loanInstallmentRepository;

    /**
     * Creates a new loan for a customer based on the provided loan request data.
     * This method performs the following actions:
     * <ul>
     *     <li>Retrieves the customer using the provided customer GUID.</li>
     *     <li>Calculates the total loan amount with interest using the loan calculation strategy.</li>
     *     <li>Validates the customer's credit limit, the number of installments, and the interest rate using predefined rules.</li>
     *     <li>Increases the customer's credit limit by the total loan amount.</li>
     *     <li>Creates the loan and associated installments, setting necessary fields like loan amount, due dates, and status.</li>
     * </ul>
     *
     * After all validations, the loan is saved to the database and returned.
     *
     * @param request the loan request data containing the customer GUID, loan amount, interest rate, and number of installments.
     * @return the created Loan entity that is saved in the repository.
     * @throws RuntimeException if the customer is not found.
     * @throws IllegalArgumentException if any of the validations for credit limit, installment count, or interest rate fail.
     */
    @Transactional
    public Loan createLoan(LoanRequestDto request) {

        Customer customer = customerService.findByGuid(request.customerGuid());

        BigDecimal totalAmount = calculationStrategy
                .calculateTotalAmount(request.amount(), request.interestRate());

        creditLimitValidator.evaluate(customer, totalAmount);
        interestRateValidator.evaluate(request.interestRate());
        installmentCountValidator.evaluate(request.numberOfInstallments());

        var loan = createLoan(request, totalAmount);

        customerService.increaseUsedCreditLimit(customer, totalAmount);

        return loan;
    }

    private Loan createLoan(LoanRequestDto request, BigDecimal totalAmount) {

        Loan loan = new Loan();
        loan.setPaid(false);
        loan.setCreateDate(now());
        loan.setCustomerGuid(request.customerGuid());
        loan.setNumberOfInstallments(request.numberOfInstallments());
        loan.setLoanAmount(request.amount().setScale(2, RoundingMode.HALF_UP));

        addInstallments(loan, totalAmount);

        return loanRepository.save(loan);
    }

    private void addInstallments(Loan loan, BigDecimal totalAmount) {
        var numberOfInstallments = loan.getNumberOfInstallments();

        BigDecimal installmentAmount = calculationStrategy
                .calculateInstallmentAmount(totalAmount, numberOfInstallments);

        List<LoanInstallment> installments = IntStream.range(1, numberOfInstallments + 1)
                .mapToObj(i -> {
                    LoanInstallment installment = new LoanInstallment();
                    installment.setLoan(loan);
                    installment.setPaid(false);
                    installment.setAmount(installmentAmount);
                    installment.setPaidAmount(BigDecimal.ZERO);
                    installment.setDueDate(getInstallmentDueDate(i));
                    return installment;
                })
                .toList();

        loan.setInstallments(installments);
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    log.warn("Loan not found with ID: {}", loanId);
                    return new LoanNotFoundException("Loan not found with id " + loanId);
                });
    }

    public List<Loan> listLoans(String customerId, Boolean isPaid, Integer numberOfInstallments) {
        return loanRepository.findByCustomerGuid(customerId).stream()
                .filter(loan -> isPaid == null || loan.isPaid() == isPaid)
                .filter(loan -> numberOfInstallments == null || loan.getNumberOfInstallments() == (numberOfInstallments))
                .toList();
    }

    public List<LoanInstallment> listInstallments(Long loanId) {
        return loanInstallmentRepository.findByLoanId(loanId);
    }

}
