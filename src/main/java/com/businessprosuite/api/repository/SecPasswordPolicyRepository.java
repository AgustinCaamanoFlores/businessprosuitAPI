package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecPasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPasswordPolicyRepository extends JpaRepository<SecPasswordPolicy, Integer> {
}