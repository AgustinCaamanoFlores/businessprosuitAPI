package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudActivityLogRepository extends JpaRepository<ActivityLog, Integer> {
}