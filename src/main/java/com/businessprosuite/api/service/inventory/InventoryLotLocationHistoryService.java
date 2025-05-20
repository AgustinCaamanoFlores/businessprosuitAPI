package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotLocationHistoryDTO;
import java.util.List;

public interface InventoryLotLocationHistoryService {
    List<InventoryLotLocationHistoryDTO> findAll();
    InventoryLotLocationHistoryDTO findById(Integer id);
    InventoryLotLocationHistoryDTO create(InventoryLotLocationHistoryDTO dto);
    InventoryLotLocationHistoryDTO update(Integer id, InventoryLotLocationHistoryDTO dto);
    void delete(Integer id);
}