package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryProductDTO;
import java.util.List;

public interface InventoryProductService {
    List<InventoryProductDTO> findAll();
    InventoryProductDTO findById(Integer id);
    InventoryProductDTO create(InventoryProductDTO dto);
    InventoryProductDTO update(Integer id, InventoryProductDTO dto);
    void delete(Integer id);
}
