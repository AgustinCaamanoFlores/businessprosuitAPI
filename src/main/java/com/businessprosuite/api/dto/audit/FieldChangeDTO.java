package com.businessprosuite.api.dto.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldChangeDTO {
    private Integer id;
    private String tableName;
    private String pkValue;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private Integer changedBy;
    private LocalDateTime changedAt;
}