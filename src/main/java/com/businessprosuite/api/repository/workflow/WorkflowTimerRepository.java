package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowTimer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTimerRepository extends JpaRepository<WorkflowTimer, Integer> {
}