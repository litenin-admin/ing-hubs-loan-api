package com.inghubs.loan.facade;

import com.inghubs.loan.domain.CustomerDTO;
import com.inghubs.loan.mappers.CustomerMapper;
import com.inghubs.loan.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomerService customerService;

    public List<CustomerDTO> getAllCustomers() {
        return CustomerMapper.INSTANCE.toDtoList(customerService.listAllCustomers());
    }

    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        var customer = customerService.createCustomer(CustomerMapper.INSTANCE.toEntity(customerDTO));
        return CustomerMapper.INSTANCE.toDto(customer);
    }

    public CustomerDTO updateCreditLimit(String customerGuid, BigDecimal creditLimit) {
        var customer = customerService.updateCreditLimit(customerGuid, creditLimit);
        return CustomerMapper.INSTANCE.toDto(customer);
    }

}
