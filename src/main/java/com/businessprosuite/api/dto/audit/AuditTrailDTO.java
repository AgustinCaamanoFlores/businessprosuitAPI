package com.businessprosuite.api.dto.audit;

import com.businessprosuite.api.model.audit.AuditTrailId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTrailDTO {
    private AuditTrailId id;
    private String tableName;
    private Integer recordId;
    private String action;
    private String changedData;
    private LocalDateTime changedAt;
    private String ip;
    private Integer userId;
    private String userAgent;
    private String country;
}