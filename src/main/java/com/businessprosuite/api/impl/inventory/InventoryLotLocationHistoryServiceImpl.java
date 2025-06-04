package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotLocationHistoryDTO;
import com.businessprosuite.api.model.inventory.InventoryLotLocationHistory;
import com.businessprosuite.api.model.inventory.InventoryLot;
import com.businessprosuite.api.model.inventory.InventoryWarehouse;
import com.businessprosuite.api.repository.inventory.InventoryLotLocationHistoryRepository;
import com.businessprosuite.api.repository.inventory.InventoryLotRepository;
import com.businessprosuite.api.repository.inventory.InventoryWarehouseRepository;
import com.businessprosuite.api.service.inventory.InventoryLotLocationHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryLotLocationHistoryServiceImpl implements InventoryLotLocationHistoryService {

    private final InventoryLotLocationHistoryRepository historyRepo;
    private final InventoryLotRepository lotRepo;
    private final InventoryWarehouseRepository whRepo;

    @Override
    public List<InventoryLotLocationHistoryDTO> findAll() {
        return historyRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryLotLocationHistoryDTO findById(Integer id) {
        InventoryLotLocationHistory history = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLotLocationHistory not found with id " + id));
        return toDto(history);
    }

    @Override
    public InventoryLotLocationHistoryDTO create(InventoryLotLocationHistoryDTO dto) {
        InventoryLot lot = lotRepo.findById(dto.getLotId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryLot not found with id " + dto.getLotId()));
        InventoryWarehouse wh = whRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryWarehouse not found with id " + dto.getWarehouseId()));

        InventoryLotLocationHistory history = new InventoryLotLocationHistory();
        history.setInventoryLot(lot);
        history.setWhs(wh);
        history.setMovedAt(dto.getMovedAt() != null ? dto.getMovedAt() : LocalDateTime.now());

        InventoryLotLocationHistory saved = historyRepo.save(history);
        return toDto(saved);
    }

    @Override
    public InventoryLotLocationHistoryDTO update(Integer id, InventoryLotLocationHistoryDTO dto) {
        InventoryLotLocationHistory history = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLotLocationHistory not found with id " + id));
        InventoryLot lot = lotRepo.findById(dto.getLotId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryLot not found with id " + dto.getLotId()));
        InventoryWarehouse wh = whRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryWarehouse not found with id " + dto.getWarehouseId()));

        history.setInventoryLot(lot);
        history.setWhs(wh);
        history.setMovedAt(dto.getMovedAt());

        InventoryLotLocationHistory updated = historyRepo.save(history);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!historyRepo.existsById(id)) {
            throw new EntityNotFoundException("InventoryLotLocationHistory not found with id " + id);
        }
        historyRepo.deleteById(id);
    }

    private InventoryLotLocationHistoryDTO toDto(InventoryLotLocationHistory history) {
        return InventoryLotLocationHistoryDTO.builder()
                .id(history.getId())
                .lotId(history.getInventoryLot().getId())
                .warehouseId(history.getWhs().getId())
                .movedAt(history.getMovedAt())
                .build();
    }
}
