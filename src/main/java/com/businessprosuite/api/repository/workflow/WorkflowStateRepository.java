package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowStateRepository extends JpaRepository<WorkflowState, Integer> {
}