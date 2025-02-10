package com.inghubs.loan.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LoanInstallmentDto(
        Long id,
        BigDecimal amount,
        BigDecimal paidAmount,
        LocalDateTime dueDate,
        boolean isPaid
) {
}
