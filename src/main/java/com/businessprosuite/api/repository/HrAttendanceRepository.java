package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.recursoshumanos.HrAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrAttendanceRepository extends JpaRepository<HrAttendance, Integer> {
}