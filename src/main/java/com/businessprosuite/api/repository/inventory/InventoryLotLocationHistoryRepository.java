package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryLotLocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLotLocationHistoryRepository extends JpaRepository<InventoryLotLocationHistory, Integer> {
}