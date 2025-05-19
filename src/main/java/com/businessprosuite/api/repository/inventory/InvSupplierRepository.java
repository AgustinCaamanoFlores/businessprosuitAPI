package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventorySupplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvSupplierRepository extends JpaRepository<InventorySupplier, Integer> {
}