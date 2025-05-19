package com.businessprosuite.api.repository.user;

import com.businessprosuite.api.model.user.UserRole;
import com.businessprosuite.api.model.user.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
