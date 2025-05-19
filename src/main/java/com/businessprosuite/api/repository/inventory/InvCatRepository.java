package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvCatRepository extends JpaRepository<InventoryCategory, Integer> {
}