package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecPasswordHistoryRepository extends JpaRepository<SecurityPasswordHistory, Integer> {
}