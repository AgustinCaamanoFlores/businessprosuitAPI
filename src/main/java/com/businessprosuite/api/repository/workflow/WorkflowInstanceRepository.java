package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Integer> {
}