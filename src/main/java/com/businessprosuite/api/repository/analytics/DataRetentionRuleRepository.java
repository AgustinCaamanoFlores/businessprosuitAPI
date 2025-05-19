package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.DataRetentionRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRetentionRuleRepository extends JpaRepository<DataRetentionRule, Integer> {
}