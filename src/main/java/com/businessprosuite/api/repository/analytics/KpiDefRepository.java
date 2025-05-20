package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.KpiDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KpiDefRepository extends JpaRepository<KpiDef, Integer> {
}