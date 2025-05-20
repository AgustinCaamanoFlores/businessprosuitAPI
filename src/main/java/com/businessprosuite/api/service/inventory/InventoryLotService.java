package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotDTO;
import java.util.List;

public interface InventoryLotService {
    List<InventoryLotDTO> findAll();
    InventoryLotDTO findById(Integer id);
    InventoryLotDTO create(InventoryLotDTO dto);
    InventoryLotDTO update(Integer id, InventoryLotDTO dto);
    void delete(Integer id);
}
