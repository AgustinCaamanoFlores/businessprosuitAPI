package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPolicyRuleRepository extends JpaRepository<SecurityPolicyRule, Integer> {
}