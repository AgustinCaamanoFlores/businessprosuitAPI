package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.data.DataRetentionRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRetentionRuleRepository extends JpaRepository<DataRetentionRule, Integer> {
}