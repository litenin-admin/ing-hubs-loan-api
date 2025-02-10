package com.inghubs.loan.controller;

import com.inghubs.loan.domain.CustomerDTO;
import com.inghubs.loan.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @Operation(summary = "Get all customers", description = "Returns a list of all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/list")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        List<CustomerDTO> customers = customerFacade.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Create a new customer", description = "Creates a new customer and returns the created customer details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {

        CustomerDTO createdCustomer = customerFacade.createNewCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @Operation(summary = "Update customer's credit limit", description = "Updates the credit limit for an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer credit limit updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{customerGuid}/update-credit-limit")
    public ResponseEntity<CustomerDTO> updateCustomerCreditLimit(
            @PathVariable String customerGuid,
            @RequestParam("creditLimit") BigDecimal creditLimit) {

        CustomerDTO updatedCustomer = customerFacade.updateCreditLimit(customerGuid, creditLimit);
        return ResponseEntity.ok(updatedCustomer);
    }

}
