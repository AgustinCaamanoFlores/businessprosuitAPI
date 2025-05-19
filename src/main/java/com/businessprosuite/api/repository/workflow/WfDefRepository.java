package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfDefRepository extends JpaRepository<WorkflowDefinition, Integer> {
}