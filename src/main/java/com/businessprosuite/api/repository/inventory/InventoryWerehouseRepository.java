package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryWerehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryWerehouseRepository extends JpaRepository<InventoryWerehouse, Integer> {
}