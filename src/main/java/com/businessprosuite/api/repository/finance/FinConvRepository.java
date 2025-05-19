package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Conv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinConvRepository extends JpaRepository<Conv, Integer> {
}