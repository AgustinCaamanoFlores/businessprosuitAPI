package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.WorkerAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerAttendanceRepository extends JpaRepository<WorkerAttendance, Integer> {
}