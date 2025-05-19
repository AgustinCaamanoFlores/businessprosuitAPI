package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.FinanceCOA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinCoaRepository extends JpaRepository<FinanceCOA, Integer> {
}