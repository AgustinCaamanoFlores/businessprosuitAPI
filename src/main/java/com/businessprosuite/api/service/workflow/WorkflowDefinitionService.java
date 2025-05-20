package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionDTO;
import java.util.List;

public interface WorkflowDefinitionService {
    List<WorkflowDefinitionDTO> findAll();
    WorkflowDefinitionDTO findById(Integer id);
    WorkflowDefinitionDTO create(WorkflowDefinitionDTO dto);
    WorkflowDefinitionDTO update(Integer id, WorkflowDefinitionDTO dto);
    void delete(Integer id);
}