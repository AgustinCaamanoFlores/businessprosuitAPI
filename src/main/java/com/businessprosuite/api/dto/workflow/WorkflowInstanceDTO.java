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
public class WorkflowInstanceDTO {
    private Integer id;
    private Integer definitionId;
    private String entityType;
    private Integer entityId;
    private Integer stateId;
    private Integer assignedToUserId;
    private LocalDateTime startDate;
}