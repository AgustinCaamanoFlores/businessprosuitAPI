package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryWarehouseRepository extends JpaRepository<InventoryWarehouse, Integer> {
}
