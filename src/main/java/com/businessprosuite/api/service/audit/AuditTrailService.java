package com.businessprosuite.api.service.audit;

import com.businessprosuite.api.dto.audit.AuditTrailDTO;
import com.businessprosuite.api.model.audit.AuditTrailId;
import java.util.List;

public interface AuditTrailService {
    List<AuditTrailDTO> findAll();
    AuditTrailDTO findById(AuditTrailId id);
    AuditTrailDTO create(AuditTrailDTO dto);
    AuditTrailDTO update(AuditTrailId id, AuditTrailDTO dto);
    void delete(AuditTrailId id);
}
