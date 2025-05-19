package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowDefinitionVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfDefVersionRepository extends JpaRepository<WorkflowDefinitionVersion, Integer> {
}