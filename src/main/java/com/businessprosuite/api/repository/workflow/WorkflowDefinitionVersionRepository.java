package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowDefinitionVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowDefinitionVersionRepository extends JpaRepository<WorkflowDefinitionVersion, Integer> {
}