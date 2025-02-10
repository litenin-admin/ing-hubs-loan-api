package com.inghubs.loan.controller;

import com.inghubs.loan.domain.*;
import com.inghubs.loan.facade.LoanFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanFacade loanFacade;

    @Operation(
            summary = "Create a new loan",
            description = "Creates a new loan for a customer with the specified amount, interest rate, and number of installments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoanDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data, such as insufficient credit limit or invalid installment count"),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error while processing the loan")
            }
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') or (@authorizationService.isRequester(#request.customerGuid()))")
    @PostMapping("/create")
    public ResponseEntity<ResponsePayload<LoanDto>> createLoan(
            @RequestBody LoanRequestDto request
    ) {
        LoanDto result = loanFacade.createLoan(request);
        return ResponseEntity.ok(new ResponsePayload<>(result));
    }

    @Operation(
            summary = "Pay installments of a loan",
            description = "Pays a specific amount towards the installments of a loan. The payment will be applied to the loan's outstanding balance, reducing the remaining installments.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment processed successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PaymentDetailDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid payment amount. The amount must be positive and cannot exceed the remaining balance."),
                    @ApiResponse(responseCode = "404", description = "Loan not found for the provided loan ID."),
                    @ApiResponse(responseCode = "409", description = "The loan is already fully paid or closed. No further payments can be processed."),
                    @ApiResponse(responseCode = "500", description = "Internal server error while processing the payment.")
            })
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') or (@authorizationService.isRequester(#request.customerGuid()))")
    @PostMapping("/pay")
    public ResponseEntity<ResponsePayload<PaymentDetailDto>> payLoan(
            @RequestBody PaymentRequestDto request
    ) {
        PaymentDetailDto result = loanFacade.payLoan(request);
        return ResponseEntity.ok(new ResponsePayload<>(result));
    }

    @Operation(
            summary = "Get loan by ID",
            description = "Fetches a loan's details by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loan details fetched successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoanDto.class))),
                    @ApiResponse(responseCode = "404", description = "Loan not found for the provided loan ID")
            })
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{loanId}")
    public ResponseEntity<ResponsePayload<LoanDto>> getLoanById(
            @Parameter(description = "The ID of the loan to retrieve") @PathVariable Long loanId
    ) {
        LoanDto result = loanFacade.getLoanById(loanId);
        return ResponseEntity.ok(new ResponsePayload<>(result));
    }

    @Operation(
            summary = "List loans by customer GUID with filters",
            description = "Fetches all loans associated with a given customer GUID with optional filters for number of installments and paid status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loans fetched successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoanDto.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') or (@authorizationService.isRequester(#customerGuid))")
    @GetMapping("/customer/{customerGuid}")
    public ResponseEntity<ResponsePayload<List<LoanDto>>> listLoans(
            @Parameter(description = "The customer ID to fetch loans for") @PathVariable String customerGuid,
            @RequestParam(required = false) Boolean isPaid,
            @RequestParam(required = false) Integer numberOfInstallments
    ) {
        List<LoanDto> result = loanFacade.listLoans(customerGuid, isPaid, numberOfInstallments);
        return ResponseEntity.ok(new ResponsePayload<>(result));
    }

    @Operation(
            summary = "List installments of a loan",
            description = "Fetches all installments for a specific loan",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Installments fetched successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoanInstallmentDto.class))),
                    @ApiResponse(responseCode = "404", description = "Loan not found for the provided loan ID")
            })
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{loanId}/installments")
    public ResponseEntity<ResponsePayload<List<LoanInstallmentDto>>> listInstallments(
            @Parameter(description = "The loan ID to fetch installments for") @PathVariable Long loanId
    ) {
        List<LoanInstallmentDto> result = loanFacade.listInstallments(loanId);
        return ResponseEntity.ok(new ResponsePayload<>(result));
    }

}
