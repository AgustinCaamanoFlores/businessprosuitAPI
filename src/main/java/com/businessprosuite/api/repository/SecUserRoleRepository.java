package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRoleRepository extends JpaRepository<SecUserRole, Integer> {
}