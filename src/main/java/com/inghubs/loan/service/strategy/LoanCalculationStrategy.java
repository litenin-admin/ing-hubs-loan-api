package com.inghubs.loan.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LoanCalculationStrategy {

    /**
     * Calculates the total amount of the loan, including the principal amount and the interest.
     *
     * @param amount The principal amount of the loan.
     * @param interestRate The interest rate to be applied to the loan.
     * @return The total amount to be paid, including the interest, rounded to two decimal places.
     */
    public BigDecimal calculateTotalAmount(BigDecimal amount, BigDecimal interestRate) {
        BigDecimal totalAmount = amount.add(amount.multiply(interestRate));
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the amount for each installment based on the total loan amount and the number of installments.
     *
     * @param totalAmount The total amount of the loan, including interest.
     * @param numberOfInstallments The number of installments to divide the total amount into.
     * @return The installment amount per period, rounded to two decimal places.
     */
    public BigDecimal calculateInstallmentAmount(BigDecimal totalAmount, int numberOfInstallments) {
        return totalAmount.divide(BigDecimal.valueOf(numberOfInstallments), RoundingMode.HALF_UP);
    }
}
