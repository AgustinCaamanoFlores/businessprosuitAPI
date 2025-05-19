package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
}