package com.businessprosuite.api.repository.user;

import com.businessprosuite.api.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Integer> {
}