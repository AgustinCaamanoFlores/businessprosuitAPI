package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPermissionRepository extends JpaRepository<SecurityPermission, Integer> {
}