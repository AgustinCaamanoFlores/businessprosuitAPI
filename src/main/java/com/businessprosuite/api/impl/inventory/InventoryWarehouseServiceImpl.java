package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWarehouseDTO;
import com.businessprosuite.api.model.inventory.InventoryWarehouse;
import com.businessprosuite.api.repository.inventory.InventoryWarehouseRepository;
import com.businessprosuite.api.service.inventory.InventoryWarehouseService;
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
public class InventoryWarehouseServiceImpl implements InventoryWarehouseService {

    private final InventoryWarehouseRepository repo;

    @Override
    public List<InventoryWarehouseDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryWarehouseDTO findById(Integer id) {
        InventoryWarehouse wh = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryWarehouse not found with id " + id));
        return toDto(wh);
    }

    @Override
    public InventoryWarehouseDTO create(InventoryWarehouseDTO dto) {
        InventoryWarehouse wh = new InventoryWarehouse();
        wh.setInvWhseName(dto.getName());
        wh.setInvWhseAddress(dto.getAddress());
        wh.setInvWhsePhone(dto.getPhone());
        wh.setInvWhseCreatedAt(LocalDateTime.now());
        wh.setInvWhseUpdatedAt(LocalDateTime.now());
        InventoryWarehouse saved = repo.save(wh);
        return toDto(saved);
    }

    @Override
    public InventoryWarehouseDTO update(Integer id, InventoryWarehouseDTO dto) {
        InventoryWarehouse wh = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryWarehouse not found with id " + id));
        wh.setInvWhseName(dto.getName());
        wh.setInvWhseAddress(dto.getAddress());
        wh.setInvWhsePhone(dto.getPhone());
        wh.setInvWhseUpdatedAt(LocalDateTime.now());
        InventoryWarehouse updated = repo.save(wh);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("InventoryWarehouse not found with id " + id);
        }
        repo.deleteById(id);
    }

    private InventoryWarehouseDTO toDto(InventoryWarehouse wh) {
        List<Integer> historyIds = wh.getInvLotLocationHistories().stream()
                .map(h -> h.getId())
                .collect(Collectors.toList());
        List<Integer> lotIds = wh.getInventoryLots().stream()
                .map(l -> l.getId())
                .collect(Collectors.toList());
        return InventoryWarehouseDTO.builder()
                .id(wh.getId())
                .name(wh.getInvWhseName())
                .address(wh.getInvWhseAddress())
                .phone(wh.getInvWhsePhone())
                .createdAt(wh.getInvWhseCreatedAt())
                .updatedAt(wh.getInvWhseUpdatedAt())
                .lotLocationHistoryIds(historyIds)
                .lotIds(lotIds)
                .build();
    }
}