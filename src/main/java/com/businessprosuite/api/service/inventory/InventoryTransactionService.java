package com.businessprosuite.api.service.inventory;

import com.businessprosuite.api.dto.inventory.InventoryTransactionDTO;
import java.util.List;

public interface InventoryTransactionService {
    List<InventoryTransactionDTO> findAll();
    InventoryTransactionDTO findById(Integer id);
    InventoryTransactionDTO create(InventoryTransactionDTO dto);
    InventoryTransactionDTO update(Integer id, InventoryTransactionDTO dto);
    void delete(Integer id);
}