package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.EtlRefreshLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlRefreshLogRepository extends JpaRepository<EtlRefreshLog, Integer> {
}