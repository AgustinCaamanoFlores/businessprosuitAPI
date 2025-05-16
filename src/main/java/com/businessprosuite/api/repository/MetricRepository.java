package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Integer> {
}