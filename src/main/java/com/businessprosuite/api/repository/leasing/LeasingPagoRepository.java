package com.businessprosuite.api.repository.leasing;

import com.businessprosuite.api.model.leasing.LeasingPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeasingPagoRepository extends JpaRepository<LeasingPayment, Integer> {
}