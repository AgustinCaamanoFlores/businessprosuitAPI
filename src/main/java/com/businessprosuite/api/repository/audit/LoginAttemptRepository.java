package com.businessprosuite.api.repository.audit;

import com.businessprosuite.api.model.audit.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Integer> {
}