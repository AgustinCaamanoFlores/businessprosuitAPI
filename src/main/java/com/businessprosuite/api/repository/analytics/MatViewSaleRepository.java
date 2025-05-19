package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.MatViewSale;
import com.businessprosuite.api.model.analytics.MatViewSaleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatViewSaleRepository extends JpaRepository<MatViewSale, MatViewSaleId> {
}