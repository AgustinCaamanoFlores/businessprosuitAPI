package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryCategoryDTO;
import java.util.List;

public interface InventoryCategoryService {
    List<InventoryCategoryDTO> findAll();
    InventoryCategoryDTO findById(Integer id);
    InventoryCategoryDTO create(InventoryCategoryDTO dto);
    InventoryCategoryDTO update(Integer id, InventoryCategoryDTO dto);
    void delete(Integer id);
}