package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventorySupplierDTO;
import java.util.List;

public interface InventorySupplierService {
    List<InventorySupplierDTO> findAll();
    InventorySupplierDTO findById(Integer id);
    InventorySupplierDTO create(InventorySupplierDTO dto);
    InventorySupplierDTO update(Integer id, InventorySupplierDTO dto);
    void delete(Integer id);
}