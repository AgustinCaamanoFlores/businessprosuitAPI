package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.FinanceConsReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinConsReportRepository extends JpaRepository<FinanceConsReport, Integer> {
}