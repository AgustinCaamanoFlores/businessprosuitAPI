package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfEstadoRepository extends JpaRepository<WorkflowState, Integer> {
}