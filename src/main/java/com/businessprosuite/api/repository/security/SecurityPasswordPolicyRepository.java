package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPasswordPolicyRepository extends JpaRepository<SecurityPasswordPolicy, Integer> {
}