package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfInstanciaRepository extends JpaRepository<WorkflowInstance, Integer> {
}