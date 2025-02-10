package com.inghubs.loan;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

public class LoanApiTest {

    @Karate.Test
    @EnabledIfSystemProperty(named = "test.payLoan", matches = "true")
    Karate testCreateLoan() {
        return Karate.run("classpath:features/createLoan.feature").relativeTo(getClass());
    }

    @Karate.Test
    @EnabledIfSystemProperty(named = "test.payLoan", matches = "true")
    Karate testPayLoan() {
        return Karate.run("classpath:features/payLoan.feature").relativeTo(getClass());
    }

}
