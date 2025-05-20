package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {
}