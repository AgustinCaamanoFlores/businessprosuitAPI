package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfHistRepository extends JpaRepository<WorkflowHistory, Integer> {
}