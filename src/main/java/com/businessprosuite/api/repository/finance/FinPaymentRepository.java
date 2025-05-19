package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinPaymentRepository extends JpaRepository<Payment, Integer> {
}