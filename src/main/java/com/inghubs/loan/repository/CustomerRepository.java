package com.inghubs.loan.repository;

import com.inghubs.loan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByGuid(String guid);
}
