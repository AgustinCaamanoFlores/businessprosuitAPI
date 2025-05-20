package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Integer> {
}