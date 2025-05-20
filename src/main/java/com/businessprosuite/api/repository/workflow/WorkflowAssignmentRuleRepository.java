package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowAssignmentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowAssignmentRuleRepository extends JpaRepository<WorkflowAssignmentRule, Integer> {
}