package com.businessprosuite.api.repository.etl;

import com.businessprosuite.api.model.etl.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlErrorLogRepository extends JpaRepository<ErrorLog, Integer> {
}