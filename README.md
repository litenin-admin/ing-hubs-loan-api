# Loan Management API

This is a Loan Management API that allows users to create loans, view loans and installments, and make payments. 
It provides functionality for managing customer loans with interest rates, installment counts, and payment history.

## Table of Contents

- [Requirements](#requirements)
- [Project Setup](#project-setup)
- [Building the Project](#building-the-project)
- [Running the Project](#running-the-project)
- [Running Tests](#running-tests) 
- [Accessing OpenAPI Documentation](#accessing-openapi-documentation)
- [Authentication](#authentication)
- [Test Users](#test-users)
- [Custom Exceptions and Error Codes](#custom-exceptions-and-error-codes)
  
## Requirements

- Java 21 (LTS)
- Maven 3.8+
- Spring Boot 3.4.2
- Postman or any API testing tool (optional, for testing API)

## Project Setup

Install dependencies with Maven:
    ```bash
    mvn clean install package
    ```

## Building the Project

If you are building the project from scratch, run the following Maven command to compile the code and package it into
a `.jar` file:

```bash
mvn clean package
```

## Running the Project

To start the Spring Boot application locally, you can run the following command:

```bash
mvn spring-boot:run # -Dspring.profiles.active=dev,prod,test
```

Alternatively, if you've already built the project (mvn clean package), you can run the jar file:

```bash
java -jar target/loan-management-api-<version>.jar
```
Additional Notes

### Database Setup

1. This project uses **H2 Database** for runtime testing, but you can configure it to use another database by modifying the `application.yml` file.
   For Liquibase-based database migrations, ensure that the appropriate change logs are configured.

2. You can customize the port by adding server.port=8081 (or another port) in the application.yml file.

By default, the API will run on port 8080. You can change the port in the application.yml file if needed.

## Running Tests

Run unit tests using Maven:

```bash
mvn test -Dtest=com.inghubs.loan.controller.LoanControllerTest

mvn test -Dtest=com.inghubs.loan.service.LoanManagementServiceTest
mvn test -Dtest=com.inghubs.loan.service.PaymentServiceTest

mvn test -Dtest=com.inghubs.loan.service.strategy.EarlyPaymentStrategyTest
mvn test -Dtest=com.inghubs.loan.service.strategy.LatePaymentStrategyTest
mvn test -Dtest=com.inghubs.loan.service.strategy.LoanCalculationStrategyTest

mvn test -Dtest=com.inghubs.loan.rules.CreditLimitValidatorTest
mvn test -Dtest=com.inghubs.loan.rules.InstallmentCountValidatorTest
mvn test -Dtest=com.inghubs.loan.rules.InterestRateValidatorTest
mvn test -Dtest=com.inghubs.loan.rules.PaymentValidatorTest
```

## Accessing OpenAPI Documentation

API documentation is provided using **SpringDoc** and can be accessed via the following endpoint once the application is running:

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec**: `http://localhost:8080/v3/api-docs`

## Authentication
This application uses **Basic Authentication** for securing access to the API. Users must provide a username and password in the HTTP headers for each request.

### Example Header

```bash
Authorization: Basic <Base64 encoded username:password>
```
### Test Users

For testing purposes, use the following credentials:

- Username: tester1@inghubs.com, tester2@inghubs.com, tester3@inghubs.com
- Password: admin

## Custom Exceptions, Error Codes

The API uses structured error codes for different failure scenarios. Below is the list of error codes and their meanings:

| Exception Class | Error Code | Description |
|----------------|-----------|-------------|
| `InsufficientCreditLimitException` | 1004 | Thrown when a customer does not have enough credit limit to take a loan. |
| `CustomerNotFoundException` | 1003 | Thrown when the requested customer ID does not exist in the system. |
| `InstallmentsNotFoundException` | 1002 | Thrown when no installments are found for the given loan ID. |
| `InvalidInterestRateException` | 1006 | Thrown when the specified interest rate is outside the valid range (0.1 to 0.5). |
| `InvalidInstallmentCountException` | 1005 | Thrown when the requested number of installments is not among the allowed values (6, 9, 12, 24). |
| `PaymentAmountExceedsInstallmentsException` | 1007 | Thrown when the payment amount is greater than the total due for the last 3 months' installments. |
| `LoanAlreadyPaidException` | 1008 | Thrown when a user tries to make a payment on a loan that has already been fully paid. |
| `LoanNotFoundException` | 1001 | Thrown when no loan is found with the given loan ID. |
| `InsufficientPaymentAmountException` | 1009 | Thrown when the payment amount is too low to cover any installments. |
