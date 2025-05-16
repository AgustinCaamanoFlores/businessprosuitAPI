package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecSessionRepository extends JpaRepository<SecSession, String> {
}