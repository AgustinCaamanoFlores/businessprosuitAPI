package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinInvoiceRepository extends JpaRepository<FinInvoice, Integer> {
}