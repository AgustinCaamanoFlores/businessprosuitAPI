package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinTaxRateRepository extends JpaRepository<TaxRate, Integer> {
}