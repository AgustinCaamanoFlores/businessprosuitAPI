package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.KpiValor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KpiValorRepository extends JpaRepository<KpiValor, Integer> {
}