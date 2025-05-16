package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.DwTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DwTimeRepository extends JpaRepository<DwTime, LocalDate> {
}