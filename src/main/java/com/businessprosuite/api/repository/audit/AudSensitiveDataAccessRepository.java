package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.SensitiveDataAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudSensitiveDataAccessRepository extends JpaRepository<SensitiveDataAccess, Integer> {
}