package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.user.UsrUserRole;
import com.businessprosuite.api.model.user.UsrUserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrUserRoleRepository extends JpaRepository<UsrUserRole, UsrUserRoleId> {
}