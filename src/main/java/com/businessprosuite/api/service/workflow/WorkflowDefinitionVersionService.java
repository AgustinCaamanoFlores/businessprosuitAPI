package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionVersionDTO;
import java.util.List;

public interface WorkflowDefinitionVersionService {
    List<WorkflowDefinitionVersionDTO> findAll();
    WorkflowDefinitionVersionDTO findById(Integer id);
    WorkflowDefinitionVersionDTO create(WorkflowDefinitionVersionDTO dto);
    WorkflowDefinitionVersionDTO update(Integer id, WorkflowDefinitionVersionDTO dto);
    void delete(Integer id);
}