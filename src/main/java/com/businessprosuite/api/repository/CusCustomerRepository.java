package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.cliente.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CusCustomerRepository extends JpaRepository<CusCustomer, Integer> {
}