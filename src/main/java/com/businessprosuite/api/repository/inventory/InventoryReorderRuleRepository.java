package com.businessprosuite.api.repository.inventory;

import com.businessprosuite.api.model.inventory.InventoryReorderRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReorderRuleRepository extends JpaRepository<InventoryReorderRule, Integer> {
    InventoryReorderRule findFirstByProd_Id(Integer prodId);
}
