package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.EtlErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlErrorLogRepository extends JpaRepository<EtlErrorLog, Integer> {
}