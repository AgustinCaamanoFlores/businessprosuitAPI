package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinTaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinTaxRateRepository extends JpaRepository<FinTaxRate, Integer> {
}