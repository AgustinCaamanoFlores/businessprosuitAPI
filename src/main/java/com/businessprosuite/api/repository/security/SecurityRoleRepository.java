package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Integer> {
}