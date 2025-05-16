package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.auditoria.AudLoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudLoginAttemptRepository extends JpaRepository<AudLoginAttempt, Integer> {
}