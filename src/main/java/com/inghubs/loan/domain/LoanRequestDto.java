package com.inghubs.loan.domain;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request data for creating a loan",
        example = "{\"customerGuid\": \"tester1@inghubs.com\", \"amount\": 12000, \"interestRate\": 0.1, \"numberOfInstallments\": 6}")
public record LoanRequestDto(

        @NotNull
        @Email(message = "Customer GUID must be a valid email address")
        @Schema(description = "The GUID of the customer (email)", example = "tester1@inghubs.com")
        String customerGuid,

        @NotNull
        @Positive(message = "Loan amount must be positive")
        @Schema(description = "The amount of the loan", example = "12000")
        BigDecimal amount,

        @NotNull
        @Positive(message = "Interest rate must be positive")
        @Schema(description = "The interest rate for the loan", example = "0.1")
        BigDecimal interestRate,

        @NotNull
        @Min(value = 1, message = "Number of installments must be at least 1")
        @Schema(description = "The number of installments for the loan", example = "6")
        Integer numberOfInstallments
) {
}
