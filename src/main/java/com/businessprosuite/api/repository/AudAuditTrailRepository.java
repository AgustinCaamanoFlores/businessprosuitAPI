package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.auditoria.AudAuditTrail;
import com.businessprosuite.api.model.auditoria.AudAuditTrailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudAuditTrailRepository extends JpaRepository<AudAuditTrail, AudAuditTrailId> {
}