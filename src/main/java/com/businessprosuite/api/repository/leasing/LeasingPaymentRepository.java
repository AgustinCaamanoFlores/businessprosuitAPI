package com.businessprosuite.api.repository.leasing;

import com.businessprosuite.api.model.leasing.LeasingPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingPaymentRepository extends JpaRepository<LeasingPayment, Integer> {
}