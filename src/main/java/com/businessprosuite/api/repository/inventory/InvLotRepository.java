package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvLotRepository extends JpaRepository<InventoryLot, Integer> {
}