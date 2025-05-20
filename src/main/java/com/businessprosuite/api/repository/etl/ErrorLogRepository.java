package com.businessprosuite.api.repository.etl;

import com.businessprosuite.api.model.etl.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Integer> {
}