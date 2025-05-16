package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.DwSale;
import com.businessprosuite.api.model.DwSaleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DwSaleRepository extends JpaRepository<DwSale, DwSaleId> {
}