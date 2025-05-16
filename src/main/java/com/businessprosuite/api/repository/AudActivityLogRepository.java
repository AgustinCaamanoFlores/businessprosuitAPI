package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.auditoria.AudActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudActivityLogRepository extends JpaRepository<AudActivityLog, Integer> {
}