package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowTimer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WfTimerRepository extends JpaRepository<WorkflowTimer, Integer> {
}