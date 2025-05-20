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
public class WorkflowTimerDTO {
    private Integer id;
    private Integer workflowInstanceId;
    private LocalDateTime dueAt;
    private String action;
    private Boolean executed;
}