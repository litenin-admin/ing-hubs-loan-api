package com.inghubs.loan.service.strategy;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.model.LoanInstallment;
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
public class EarlyPaymentStrategy implements LoanPaymentStrategy {

    private final LoanProperties loanProperties;

    /**
     * Processes an early payment for a loan installment.
     * <p>
     * If the payment is made before the due date, the installment will have a discount applied based
     * on how early the payment is made. The installment will be marked as paid if the remaining amount
     * covers the discounted installment amount.
     * </p>
     *
     * @param installment The loan installment to be paid.
     */
    @Override
    public void processPayment(LoanInstallment installment) {

        BigDecimal remaining = installment.getAmount().subtract(installment.getPaidAmount());
        BigDecimal discountedAmount = installment.getAmount().subtract(getDiscount(installment));

        // Update the paid amount if the remaining amount can cover the discounted installment
        if (remaining.compareTo(discountedAmount) >= 0) {
            installment.setPaid(true);
            installment.setPaymentDate(now());
            installment.setPaidAmount(discountedAmount);

            log.debug("(early-payment)| installment id: {} fully paid with discounted amount: {}",
                    installment.getId(), installment.getPaidAmount());

            return;
        }

        // If the remaining amount can't cover the discounted installment, make a partial payment
        installment.setPaidAmount(remaining);

        log.debug("(early-payment)| installment id: {} partially paid. remaining amount: {}",
                installment.getId(), installment.getPaidAmount());
    }

    private BigDecimal getDiscount(LoanInstallment installment) {
        return installment.getAmount()
                .multiply(loanProperties.getDiscountRate())
                .multiply(BigDecimal.valueOf(getDaysBeforeDueDate(installment)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private long getDaysBeforeDueDate(LoanInstallment installment) {
        LocalDateTime currentDate = now();
        return ChronoUnit.DAYS.between(currentDate, installment.getDueDate());
    }

}
