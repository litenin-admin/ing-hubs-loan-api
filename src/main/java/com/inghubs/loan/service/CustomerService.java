package com.inghubs.loan.service;

import com.inghubs.loan.error.exception.CustomerNotFoundException;
import com.inghubs.loan.model.Customer;
import com.inghubs.loan.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findByGuid(String customerId) {
        return customerRepository.findByGuid(customerId)
                .orElseThrow(() -> {
                    log.warn("Customer not found with GUID: {}", customerId);
                    return new CustomerNotFoundException("Customer not found with GUID: " + customerId);
                });
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCreditLimit(String customerGuid, BigDecimal creditLimit) {
        Customer customer = findByGuid(customerGuid);
        customer.setCreditLimit(creditLimit.setScale(2, RoundingMode.HALF_UP));
        return customerRepository.save(customer);
    }

    public void decreaseUsedCreditLimit(String customerGuid, BigDecimal amount) {
        Customer customer = findByGuid(customerGuid);
        var used = customer.getUsedCreditLimit()
                .subtract(amount)
                .setScale(2, RoundingMode.HALF_UP);

        updateUsedCreditLimit(customer, used);
    }

    public void increaseUsedCreditLimit(Customer customer, BigDecimal totalAmount) {
        var used = customer.getUsedCreditLimit()
                .add(totalAmount)
                .setScale(2, RoundingMode.HALF_UP);

        updateUsedCreditLimit(customer, used);
    }

    private void updateUsedCreditLimit(Customer customer, BigDecimal used) {
        customer.setUsedCreditLimit(used);
        customerRepository.save(customer);
    }

}
