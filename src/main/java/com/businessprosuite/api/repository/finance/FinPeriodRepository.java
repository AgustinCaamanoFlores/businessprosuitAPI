package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinPeriodRepository extends JpaRepository<Period, Integer> {
}