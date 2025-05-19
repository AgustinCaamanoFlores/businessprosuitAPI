package com.businessprosuite.api.repository.customer;

import com.businessprosuite.api.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CusCustomerRepository extends JpaRepository<Customer, Integer> {
}