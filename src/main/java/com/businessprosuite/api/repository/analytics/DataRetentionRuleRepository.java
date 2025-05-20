package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.DataRetentionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRetentionRuleRepository extends JpaRepository<DataRetentionRule, Integer> {
}