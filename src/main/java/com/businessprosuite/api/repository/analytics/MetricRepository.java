package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Integer> {
}