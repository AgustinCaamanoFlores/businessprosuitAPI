package com.businessprosuite.api.service.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowAssignmentRuleDTO;
import java.util.List;

public interface WorkflowAssignmentRuleService {
    List<WorkflowAssignmentRuleDTO> findAll();
    WorkflowAssignmentRuleDTO findById(Integer id);
    WorkflowAssignmentRuleDTO create(WorkflowAssignmentRuleDTO dto);
    WorkflowAssignmentRuleDTO update(Integer id, WorkflowAssignmentRuleDTO dto);
    void delete(Integer id);
}