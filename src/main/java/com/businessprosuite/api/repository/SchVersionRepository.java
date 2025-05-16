package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.SchVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchVersionRepository extends JpaRepository<SchVersion, Integer> {
}