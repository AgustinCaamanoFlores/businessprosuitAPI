package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRoleRepository extends JpaRepository<SecurityUserRole, Integer> {
}