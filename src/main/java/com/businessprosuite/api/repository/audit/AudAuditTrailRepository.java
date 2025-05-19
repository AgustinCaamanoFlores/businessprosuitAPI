package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.AuditTrail;
import com.businessprosuite.api.model.audit.AuditTrailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudAuditTrailRepository extends JpaRepository<AuditTrail, AuditTrailId> {
}