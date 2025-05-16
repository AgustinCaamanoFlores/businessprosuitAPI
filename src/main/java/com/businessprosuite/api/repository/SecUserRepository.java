package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRepository extends JpaRepository<SecUser, Integer> {
}