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

- **Swagger UI**: `http://localhost:8088/swagger-ui.html`
- **OpenAPI Spec**: `http://localhost:8088/v3/api-docs`

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

---

## Pay Loan Request

### Endpoint
`POST /api/loans/pay`

### Request Body Example
```json
{
  "customerGuid": "tester1@inghubs.com",
  "amount": 2200.00,
  "loanId": 1
}
```

### Request Parameters
| Field           | Type    | Description |
|----------------|--------|-------------|
| `customerGuid` | String | Unique identifier for the customer (email format) |
| `amount`       | Number | Payment amount towards the loan |
| `loanId`       | Integer | Unique identifier of the loan being paid |

### Response Example
```json
{
  "data": {
    "description": "Paid 1 installments. Total Paid: 2160.40. The loan is not yet fully paid.",
    "installments": [
      {
        "id": 1,
        "amount": 2200,
        "paidAmount": 2160.4,
        "dueDate": "2025-03-01T00:00:00",
        "isPaid": true
      }
    ]
  },
  "error": null
}
```

---