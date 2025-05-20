package com.businessprosuite.api.repository.core;

import com.businessprosuite.api.model.core.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
}