package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProductRepository extends JpaRepository<InventoryProduct, Integer> {
}