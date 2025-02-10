package com.inghubs.loan.domain;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Request data for making a payment on a loan",
        example = "{\"customerGuid\": \"tester1@inghubs.com\", \"amount\": 12000, \"loanId\": 1}")
public record PaymentRequestDto(

        @NotNull
        @Email(message = "Customer GUID must be a valid email address")
        @Schema(description = "The GUID of the customer (email)", example = "tester1@inghubs.com")
        String customerGuid,

        @NotNull
        @Positive(message = "Payment amount must be positive")
        @Schema(description = "The payment amount to be made towards the loan", example = "12000")
        BigDecimal amount,

        @NotNull
        @Schema(description = "The ID of the loan for which the payment is being made", example = "1")
        Long loanId
) {}
