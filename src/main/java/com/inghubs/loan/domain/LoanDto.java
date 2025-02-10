package com.inghubs.loan.domain;

import java.math.BigDecimal;
import java.util.List;

public record LoanDto(
        Long id,
        String customerGuid,
        BigDecimal loanAmount,
        int numberOfInstallments,
        boolean isPaid,
        List<LoanInstallmentDto> installments
) {
}
