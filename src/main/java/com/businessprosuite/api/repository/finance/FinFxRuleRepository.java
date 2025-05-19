package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.FiscalRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinFxRuleRepository extends JpaRepository<FiscalRule, Integer> {
}