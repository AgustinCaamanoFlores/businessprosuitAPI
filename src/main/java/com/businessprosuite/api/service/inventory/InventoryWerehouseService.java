package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWerehouseDTO;
import java.util.List;

public interface InventoryWerehouseService {
    List<InventoryWerehouseDTO> findAll();
    InventoryWerehouseDTO findById(Integer id);
    InventoryWerehouseDTO create(InventoryWerehouseDTO dto);
    InventoryWerehouseDTO update(Integer id, InventoryWerehouseDTO dto);
    void delete(Integer id);
}