package com.inghubs.loan.mappers;

import com.inghubs.loan.domain.CustomerDTO;
import com.inghubs.loan.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDto(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);

    List<CustomerDTO> toDtoList(List<Customer> customers);

    List<Customer> toEntityList(List<CustomerDTO> customerDTOs);
}
