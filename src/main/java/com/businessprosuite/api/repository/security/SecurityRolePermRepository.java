package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityRolePerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRolePermRepository extends JpaRepository<SecurityRolePerm, Integer> {
}