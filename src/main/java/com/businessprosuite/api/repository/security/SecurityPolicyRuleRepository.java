package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPolicyRuleRepository extends JpaRepository<SecurityPolicyRule, Integer> {
}