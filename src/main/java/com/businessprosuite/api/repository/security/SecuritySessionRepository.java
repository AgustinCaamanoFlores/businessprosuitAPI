package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecuritySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecuritySessionRepository extends JpaRepository<SecuritySession, String> {
}