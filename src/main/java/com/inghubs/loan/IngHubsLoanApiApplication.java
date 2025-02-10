package com.inghubs.loan;

import com.inghubs.loan.config.LoanProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LoanProperties.class)
//(exclude={DataSourceAutoConfiguration.class})
public class IngHubsLoanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngHubsLoanApiApplication.class, args);
	}

}
