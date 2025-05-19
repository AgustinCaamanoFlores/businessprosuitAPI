package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryReorderRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvReorderRuleRepository extends JpaRepository<InventoryReorderRule, Integer> {
}