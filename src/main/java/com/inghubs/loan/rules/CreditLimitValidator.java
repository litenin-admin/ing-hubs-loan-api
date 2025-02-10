package com.inghubs.loan.rules;

import com.inghubs.loan.error.exception.InsufficientCreditLimitException;
import com.inghubs.loan.model.Customer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.inghubs.loan.error.ValidationMessages.INSUFFICIENT_CREDIT_LIMIT;

@Component
public class CreditLimitValidator {

    public void evaluate(Customer customer, BigDecimal totalAmount) {
        var totalAmountUsed = customer.getUsedCreditLimit().add(totalAmount);

        if (totalAmountUsed.compareTo(customer.getCreditLimit()) > 0) {
            throw new InsufficientCreditLimitException(INSUFFICIENT_CREDIT_LIMIT);
        }
    }

}
