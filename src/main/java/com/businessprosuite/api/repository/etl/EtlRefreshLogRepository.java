package com.businessprosuite.api.repository.etl;

import com.businessprosuite.api.model.etl.RefreshLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlRefreshLogRepository extends JpaRepository<RefreshLog, Integer> {
}