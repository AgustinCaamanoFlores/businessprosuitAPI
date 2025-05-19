package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPasswordPolicyRepository extends JpaRepository<SecurityPasswordPolicy, Integer> {
}