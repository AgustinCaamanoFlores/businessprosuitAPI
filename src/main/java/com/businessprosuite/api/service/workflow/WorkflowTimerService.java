package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowTimerDTO;
import java.util.List;

public interface WorkflowTimerService {
    List<WorkflowTimerDTO> findAll();
    WorkflowTimerDTO findById(Integer id);
    WorkflowTimerDTO create(WorkflowTimerDTO dto);
    WorkflowTimerDTO update(Integer id, WorkflowTimerDTO dto);
    void delete(Integer id);
}