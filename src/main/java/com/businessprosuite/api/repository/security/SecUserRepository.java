package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRepository extends JpaRepository<SecurityUser, Integer> {
}