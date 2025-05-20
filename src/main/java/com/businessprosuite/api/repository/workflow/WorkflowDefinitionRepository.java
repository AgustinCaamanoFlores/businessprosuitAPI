package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, Integer> {
}