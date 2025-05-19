package com.businessprosuite.api.repository.config;

import com.businessprosuite.api.model.config.ConfigCompliance;
import com.businessprosuite.api.model.config.ConfigComplianceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigComplianceRepository extends JpaRepository<ConfigCompliance, ConfigComplianceId> {
}