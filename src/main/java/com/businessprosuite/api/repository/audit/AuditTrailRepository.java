package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.AuditTrail;
import com.businessprosuite.api.model.audit.AuditTrailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, AuditTrailId> {
}