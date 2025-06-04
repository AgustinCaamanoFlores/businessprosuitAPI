package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWarehouseDTO;
import java.util.List;

public interface InventoryWarehouseService {
    List<InventoryWarehouseDTO> findAll();
    InventoryWarehouseDTO findById(Integer id);
    InventoryWarehouseDTO create(InventoryWarehouseDTO dto);
    InventoryWarehouseDTO update(Integer id, InventoryWarehouseDTO dto);
    void delete(Integer id);
}
