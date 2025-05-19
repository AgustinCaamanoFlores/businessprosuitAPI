package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecuritySession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecSessionRepository extends JpaRepository<SecuritySession, String> {
}