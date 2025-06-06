package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Integer> {
}