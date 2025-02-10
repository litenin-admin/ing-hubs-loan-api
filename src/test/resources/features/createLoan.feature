Feature: Test Create Loan Endpoint

  Background:
    * url 'http://localhost:8080/api/v1/loans'

  Scenario: Successfully create a new loan
    Given request { "customerGuid": "tester1@inghubs.com", "amount": 12000, "interestRate": 0.1, "numberOfInstallments": 6 }
    When method post
    Then status 200
    And match response.data.customerGuid == 'tester1@inghubs.com'
    And match response.data.loanAmount == 12000
    And match response.data.numberOfInstallments == 6
