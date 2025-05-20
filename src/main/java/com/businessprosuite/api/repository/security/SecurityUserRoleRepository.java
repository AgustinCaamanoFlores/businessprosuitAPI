package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRoleRepository extends JpaRepository<SecurityUserRole, Integer> {
}