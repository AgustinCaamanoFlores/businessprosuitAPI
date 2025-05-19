package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinInvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
}