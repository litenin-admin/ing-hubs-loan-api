package com.inghubs.loan.rules;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.error.exception.InvalidInterestRateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InterestRateValidator {

    private final LoanProperties loanProperties;

    public void evaluate(BigDecimal interestRate) {
        var minInterestRate = loanProperties.getMinInterestRate();
        var maxInterestRate = loanProperties.getMaxInterestRate();

        if (interestRate.compareTo(minInterestRate) < 0
                || interestRate.compareTo(loanProperties.getMaxInterestRate()) > 0) {
            throw new InvalidInterestRateException(
                    "Interest rate must be between " + minInterestRate + " and " + maxInterestRate);
        }
    }

}
