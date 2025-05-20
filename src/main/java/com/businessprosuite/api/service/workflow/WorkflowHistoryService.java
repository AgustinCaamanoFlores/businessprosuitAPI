package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowHistoryDTO;
import java.util.List;

public interface WorkflowHistoryService {
    List<WorkflowHistoryDTO> findAll();
    WorkflowHistoryDTO findById(Integer id);
    WorkflowHistoryDTO create(WorkflowHistoryDTO dto);
    WorkflowHistoryDTO update(Integer id, WorkflowHistoryDTO dto);
    void delete(Integer id);
}