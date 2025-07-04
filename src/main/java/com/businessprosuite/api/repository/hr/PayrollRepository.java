package com.businessprosuite.api.repository.hr;

import com.businessprosuite.api.model.hr.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
}
