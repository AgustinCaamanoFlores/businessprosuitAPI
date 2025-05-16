package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.auditoria.AudSensitiveDataAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudSensitiveDataAccessRepository extends JpaRepository<AudSensitiveDataAccess, Integer> {
}