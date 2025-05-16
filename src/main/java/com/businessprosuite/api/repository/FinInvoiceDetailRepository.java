package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinInvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinInvoiceDetailRepository extends JpaRepository<FinInvoiceDetail, Integer> {
}