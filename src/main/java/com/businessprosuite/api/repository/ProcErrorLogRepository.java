package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.ProcErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcErrorLogRepository extends JpaRepository<ProcErrorLog, Integer> {
}