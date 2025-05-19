package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowAssignmentRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfAssignmentRuleRepository extends JpaRepository<WorkflowAssignmentRule, Integer> {
}