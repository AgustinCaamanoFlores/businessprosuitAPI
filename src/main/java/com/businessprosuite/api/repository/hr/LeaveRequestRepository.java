package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
}