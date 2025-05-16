package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.configuraciones.CfgCompliance;
import com.businessprosuite.api.model.configuraciones.CfgComplianceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgComplianceRepository extends JpaRepository<CfgCompliance, CfgComplianceId> {
}