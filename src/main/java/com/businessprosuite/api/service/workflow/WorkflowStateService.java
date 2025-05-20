package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowStateDTO;
import java.util.List;

public interface WorkflowStateService {
    List<WorkflowStateDTO> findAll();
    WorkflowStateDTO findById(Integer id);
    WorkflowStateDTO create(WorkflowStateDTO dto);
    WorkflowStateDTO update(Integer id, WorkflowStateDTO dto);
    void delete(Integer id);
}