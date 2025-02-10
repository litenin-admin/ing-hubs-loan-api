package com.inghubs.loan.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "loan")
@Getter
@Setter
public class LoanProperties {

    private BigDecimal minInterestRate;
    private BigDecimal maxInterestRate;
    private BigDecimal penaltyRate;
    private BigDecimal discountRate;
    private List<Integer> validInstallmentCounts;
}
