package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
}