package com.businessprosuite.api.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataRetentionRuleDTO {
    private Integer id;
    private String tableName;
    private Integer keepMonths;
    private LocalDateTime lastRun;
}