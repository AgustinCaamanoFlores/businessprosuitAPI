package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.DwTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DwTimeRepository extends JpaRepository<DwTime, LocalDate> {
}