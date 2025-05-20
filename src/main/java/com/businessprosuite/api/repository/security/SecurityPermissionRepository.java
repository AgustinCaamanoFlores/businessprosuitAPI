package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPermissionRepository extends JpaRepository<SecurityPermission, Integer> {
}