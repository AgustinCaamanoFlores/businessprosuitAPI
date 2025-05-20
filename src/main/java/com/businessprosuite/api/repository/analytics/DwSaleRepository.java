package com.businessprosuite.api.repository.analytics;

import com.businessprosuite.api.model.analytics.DwSale;
import com.businessprosuite.api.model.analytics.DwSaleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DwSaleRepository extends JpaRepository<DwSale, DwSaleId> {
}