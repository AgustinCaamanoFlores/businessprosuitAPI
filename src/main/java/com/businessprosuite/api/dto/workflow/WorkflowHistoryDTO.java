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
public class WorkflowHistoryDTO {
    private Integer id;
    private Integer workflowInstanceId;
    private Integer fromState;
    private Integer toState;
    private LocalDateTime eventDate;
    private Integer userId;
}