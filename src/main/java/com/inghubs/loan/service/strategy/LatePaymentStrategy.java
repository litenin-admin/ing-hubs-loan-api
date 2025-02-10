package com.inghubs.loan.service.strategy;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.model.LoanInstallment;
import com.inghubs.loan.utils.Dates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.inghubs.loan.utils.Dates.now;

@Component
@Slf4j
@RequiredArgsConstructor
public class LatePaymentStrategy implements LoanPaymentStrategy {

    private final LoanProperties loanProperties;

    /**
     * Processes a late payment for a loan installment.
     * <p>
     * If the payment is made after the due date, a penalty is added to the installment amount. The
     * installment will be marked as paid if the remaining amount can cover the penalized installment amount.
     * </p>
     *
     * @param installment The loan installment to be paid.
     */
    @Override
    public void processPayment(LoanInstallment installment) {

        BigDecimal remaining = installment.getAmount().subtract(installment.getPaidAmount());
        BigDecimal penalizedAmount = installment.getAmount().add(getPenalty(installment));

        // Update the paid amount if the remaining amount can cover the penalized installment
        if (remaining.compareTo(penalizedAmount) >= 0) {
            installment.setPaid(true);
            installment.setPaymentDate(now());
            installment.setPaidAmount(penalizedAmount);

            log.debug("installment id: {} fully paid with penalized amount: {}"
                    , installment.getId(), installment.getPaidAmount());

            return;
        }

        // If the remaining amount is less than the penalized installment, set the paid amount to the remaining value
        installment.setPaidAmount(remaining);

        log.debug("(late-payment)| installment id: {} partially paid. remaining amount: {}",
                installment.getId(), installment.getPaidAmount());
    }

    private BigDecimal getPenalty(LoanInstallment installment) {
        return installment.getAmount()
                .multiply(loanProperties.getPenaltyRate())
                .multiply(BigDecimal.valueOf(getDaysAfterDueDate(installment)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private long getDaysAfterDueDate(LoanInstallment installment) {
        LocalDateTime currentDate = Dates.now();
        return ChronoUnit.DAYS.between(installment.getDueDate(), currentDate);
    }

}
