package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPasswordHistoryRepository extends JpaRepository<SecPasswordHistory, Integer> {
}