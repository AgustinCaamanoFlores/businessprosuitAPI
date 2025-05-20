package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}