package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudLoginAttemptRepository extends JpaRepository<LoginAttempt, Integer> {
}