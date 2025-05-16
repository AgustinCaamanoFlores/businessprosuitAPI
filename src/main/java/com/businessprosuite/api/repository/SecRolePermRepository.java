package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecRolePerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecRolePermRepository extends JpaRepository<SecRolePerm, Integer> {
}