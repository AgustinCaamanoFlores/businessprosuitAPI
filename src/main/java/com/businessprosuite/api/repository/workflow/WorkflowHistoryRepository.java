package com.businessprosuite.api.repository.workflow;

import com.businessprosuite.api.model.workflow.WorkflowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowHistoryRepository extends JpaRepository<WorkflowHistory, Integer> {
}