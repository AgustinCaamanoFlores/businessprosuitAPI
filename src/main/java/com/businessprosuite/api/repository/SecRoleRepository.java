package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecRoleRepository extends JpaRepository<SecRole, Integer> {
}