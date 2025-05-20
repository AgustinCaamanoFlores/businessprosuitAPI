package com.businessprosuite.api.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowDefinitionDTO {
    private Integer id;
    private String name;
    private Integer companyId;
}