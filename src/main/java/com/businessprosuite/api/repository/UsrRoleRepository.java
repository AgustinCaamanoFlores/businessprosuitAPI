package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.user.UsrRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrRoleRepository extends JpaRepository<UsrRole, Integer> {
}