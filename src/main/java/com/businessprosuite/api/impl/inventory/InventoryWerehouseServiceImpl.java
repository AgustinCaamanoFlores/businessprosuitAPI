package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWerehouseDTO;
import com.businessprosuite.api.model.inventory.InventoryWerehouse;
import com.businessprosuite.api.repository.inventory.InventoryWerehouseRepository;
import com.businessprosuite.api.service.inventory.InventoryWerehouseService;
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
public class InventoryWerehouseServiceImpl implements InventoryWerehouseService {

    private final InventoryWerehouseRepository repo;

    @Override
    public List<InventoryWerehouseDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryWerehouseDTO findById(Integer id) {
        InventoryWerehouse wh = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryWerehouse not found with id " + id));
        return toDto(wh);
    }

    @Override
    public InventoryWerehouseDTO create(InventoryWerehouseDTO dto) {
        InventoryWerehouse wh = new InventoryWerehouse();
        wh.setInvWhseName(dto.getName());
        wh.setInvWhseAddress(dto.getAddress());
        wh.setInvWhsePhone(dto.getPhone());
        wh.setInvWhseCreatedAt(LocalDateTime.now());
        wh.setInvWhseUpdatedAt(LocalDateTime.now());
        InventoryWerehouse saved = repo.save(wh);
        return toDto(saved);
    }

    @Override
    public InventoryWerehouseDTO update(Integer id, InventoryWerehouseDTO dto) {
        InventoryWerehouse wh = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryWerehouse not found with id " + id));
        wh.setInvWhseName(dto.getName());
        wh.setInvWhseAddress(dto.getAddress());
        wh.setInvWhsePhone(dto.getPhone());
        wh.setInvWhseUpdatedAt(LocalDateTime.now());
        InventoryWerehouse updated = repo.save(wh);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("InventoryWerehouse not found with id " + id);
        }
        repo.deleteById(id);
    }

    private InventoryWerehouseDTO toDto(InventoryWerehouse wh) {
        List<Integer> historyIds = wh.getInvLotLocationHistories().stream()
                .map(h -> h.getId())
                .collect(Collectors.toList());
        List<Integer> lotIds = wh.getInventoryLots().stream()
                .map(l -> l.getId())
                .collect(Collectors.toList());
        return InventoryWerehouseDTO.builder()
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