package com.inghubs.loan.domain;

import java.util.List;

public record PaymentDetailDto(
        String description,
        List<LoanInstallmentDto> installments
) {
}
