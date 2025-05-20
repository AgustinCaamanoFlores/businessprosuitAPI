package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}