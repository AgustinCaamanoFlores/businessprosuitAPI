package com.businessprosuite.api.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStateDTO {
    private Integer id;
    private Integer definitionId;
    private String name;
    private Integer order;
}
