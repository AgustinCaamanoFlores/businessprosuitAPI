package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityPasswordHistoryRepository extends JpaRepository<SecurityPasswordHistory, Integer> {
}