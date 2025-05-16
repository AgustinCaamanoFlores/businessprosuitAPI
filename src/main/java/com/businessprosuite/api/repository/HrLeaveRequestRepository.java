package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.recursoshumanos.HrLeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrLeaveRequestRepository extends JpaRepository<HrLeaveRequest, Integer> {
}