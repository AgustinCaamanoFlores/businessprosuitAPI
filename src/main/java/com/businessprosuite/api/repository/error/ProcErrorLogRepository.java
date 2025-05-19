package com.businessprosuite.api.repository.error;

import com.businessprosuite.api.model.error.ProcErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcErrorLogRepository extends JpaRepository<ProcErrorLog, Integer> {
}