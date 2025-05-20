package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer> {
}