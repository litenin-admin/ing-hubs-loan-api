package com.inghubs.loan.rules;

import com.inghubs.loan.config.LoanProperties;
import com.inghubs.loan.error.exception.InvalidInstallmentCountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstallmentCountValidator {

    private final LoanProperties loanProperties;

    public void evaluate(Integer numberOfInstallments) {
        var installmentCounts = loanProperties.getValidInstallmentCounts();

        if (!installmentCounts.contains(numberOfInstallments)) {
            throw new InvalidInstallmentCountException(
                    "Invalid number of installments. Valid values are " + installmentCounts);
        }
    }

}
