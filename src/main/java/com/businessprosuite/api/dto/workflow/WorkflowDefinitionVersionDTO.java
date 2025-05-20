package com.businessprosuite.api.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowDefinitionVersionDTO {
    private Integer id;
    private Integer workflowDefinitionId;
    private String snapshotJson;
    private LocalDateTime effectiveFrom;
}