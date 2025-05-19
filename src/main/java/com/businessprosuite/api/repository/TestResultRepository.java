package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.core.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
}