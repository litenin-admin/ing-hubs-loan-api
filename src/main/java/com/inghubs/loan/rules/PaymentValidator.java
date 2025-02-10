package com.inghubs.loan.rules;

import com.inghubs.loan.error.ValidationMessages;
import com.inghubs.loan.error.exception.InsufficientPaymentAmountException;
import com.inghubs.loan.error.exception.PaymentAmountExceedsInstallmentsException;
import com.inghubs.loan.model.LoanInstallment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PaymentValidator {

    public void evaluate(List<LoanInstallment> installments, BigDecimal amount) {

        if (amount.compareTo(installments.getFirst().getAmount()) < 0) {
            throw new InsufficientPaymentAmountException(ValidationMessages.PAYMENT_TOO_LOW);
        }

        // Calculate the total amount due for the installments
        BigDecimal totalDueForInstallments = installments.stream()
                .map(LoanInstallment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Check if the payment amount is greater than the total due for installments
        if (amount.compareTo(totalDueForInstallments) > 0) {
            throw new PaymentAmountExceedsInstallmentsException(ValidationMessages.PAYMENT_EXCEEDS_TOTAL);
        }

        // Check if the payment amount is divisible by the exact installment amounts
        BigDecimal remainingAmount = amount;
        for (LoanInstallment installment : installments) {
            if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            if (remainingAmount.compareTo(installment.getAmount()) < 0) {
                throw new InsufficientPaymentAmountException(ValidationMessages.PAYMENT_NOT_MATCHING_INSTALLMENTS);
            }
            remainingAmount = remainingAmount.subtract(installment.getAmount());
        }
    }

}
