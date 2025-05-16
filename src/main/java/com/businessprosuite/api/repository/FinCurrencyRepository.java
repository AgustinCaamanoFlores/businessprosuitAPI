package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinCurrencyRepository extends JpaRepository<FinCurrency, Integer> {
}