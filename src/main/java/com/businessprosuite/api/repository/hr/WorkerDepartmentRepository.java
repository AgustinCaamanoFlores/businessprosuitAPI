package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.WorkerDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerDepartmentRepository extends JpaRepository<WorkerDepartment, Integer> {
}