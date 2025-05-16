package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecPolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPolicyRuleRepository extends JpaRepository<SecPolicyRule, Integer> {
}