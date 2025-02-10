Feature: Test Pay Loan Endpoint

  Background:
    * url 'http://localhost:8080/api/v1/loans'

  Scenario: Successfully pay a loan
    Given request { "customerGuid": "tester1@inghubs.com", "amount": 12000, "loanId": 1 }
    When method post
    Then status 200
    And match response.data.customerGuid == 'tester1@inghubs.com'
    And match response.data.loanId == 1
    And match response.data.amount == 12000
