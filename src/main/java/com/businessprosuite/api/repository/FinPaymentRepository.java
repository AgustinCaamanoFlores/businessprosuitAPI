package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinPaymentRepository extends JpaRepository<FinPayment, Integer> {
}