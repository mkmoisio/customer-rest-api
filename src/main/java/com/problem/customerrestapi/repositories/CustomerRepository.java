package com.problem.customerrestapi.repositories;

import com.problem.customerrestapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
