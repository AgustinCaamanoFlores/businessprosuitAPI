package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Integer> {
}