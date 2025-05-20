package com.businessprosuite.api.repository.etl;

import com.businessprosuite.api.model.etl.RefreshLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshLogRepository extends JpaRepository<RefreshLog, Integer> {
}