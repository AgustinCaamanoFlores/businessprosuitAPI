package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityRolePerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecRolePermRepository extends JpaRepository<SecurityRolePerm, Integer> {
}