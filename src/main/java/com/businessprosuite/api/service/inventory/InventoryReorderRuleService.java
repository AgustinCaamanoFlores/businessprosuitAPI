package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryReorderRuleDTO;
import java.util.List;

public interface InventoryReorderRuleService {
    List<InventoryReorderRuleDTO> findAll();
    InventoryReorderRuleDTO findById(Integer id);
    InventoryReorderRuleDTO create(InventoryReorderRuleDTO dto);
    InventoryReorderRuleDTO update(Integer id, InventoryReorderRuleDTO dto);
    void delete(Integer id);
}