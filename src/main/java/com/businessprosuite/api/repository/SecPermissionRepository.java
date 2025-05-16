package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPermissionRepository extends JpaRepository<SecPermission, Integer> {
}