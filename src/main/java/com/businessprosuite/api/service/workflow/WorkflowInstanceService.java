package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowInstanceDTO;
import java.util.List;

public interface WorkflowInstanceService {
    List<WorkflowInstanceDTO> findAll();
    WorkflowInstanceDTO findById(Integer id);
    WorkflowInstanceDTO create(WorkflowInstanceDTO dto);
    WorkflowInstanceDTO update(Integer id, WorkflowInstanceDTO dto);
    void delete(Integer id);
}