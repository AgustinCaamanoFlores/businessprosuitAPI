package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.DwTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface DwTimeRepository extends JpaRepository<DwTime, LocalDate> {
}