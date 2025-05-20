package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryLotDTO;
import com.businessprosuite.api.model.inventory.InventoryLot;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.inventory.InventoryWerehouse;
import com.businessprosuite.api.repository.inventory.InventoryLotRepository;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.repository.inventory.InventoryWerehouseRepository;
import com.businessprosuite.api.service.inventory.InventoryLotService;
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
public class InventoryLotServiceImpl implements InventoryLotService {

    private final InventoryLotRepository lotRepo;
    private final InventoryProductRepository productRepo;
    private final InventoryWerehouseRepository warehouseRepo;

    @Override
    public List<InventoryLotDTO> findAll() {
        return lotRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryLotDTO findById(Integer id) {
        InventoryLot lot = lotRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLot not found with id " + id));
        return toDto(lot);
    }

    @Override
    public InventoryLotDTO create(InventoryLotDTO dto) {
        InventoryProduct product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));
        InventoryLot lot = new InventoryLot();
        lot.setInvLotProd(product);
        lot.setInvLotNumber(dto.getLotNumber());
        lot.setInvLotExpirationDate(dto.getExpirationDate());
        lot.setInvLotQuantity(dto.getQuantity());
        if (dto.getWarehouseId() != null) {
            InventoryWerehouse wh = warehouseRepo.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("InventoryWerehouse not found with id " + dto.getWarehouseId()));
            lot.setInvLotWhs(wh);
        }
        lot.setInvLotCreatedAt(LocalDateTime.now());
        InventoryLot saved = lotRepo.save(lot);
        return toDto(saved);
    }

    @Override
    public InventoryLotDTO update(Integer id, InventoryLotDTO dto) {
        InventoryLot lot = lotRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLot not found with id " + id));
        InventoryProduct product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + dto.getProductId()));
        lot.setInvLotProd(product);
        lot.setInvLotNumber(dto.getLotNumber());
        lot.setInvLotExpirationDate(dto.getExpirationDate());
        lot.setInvLotQuantity(dto.getQuantity());
        if (dto.getWarehouseId() != null) {
            InventoryWerehouse wh = warehouseRepo.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("InventoryWerehouse not found with id " + dto.getWarehouseId()));
            lot.setInvLotWhs(wh);
        } else {
            lot.setInvLotWhs(null);
        }
        lot.setInvLotCreatedAt(lot.getInvLotCreatedAt());
        InventoryLot updated = lotRepo.save(lot);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!lotRepo.existsById(id)) {
            throw new EntityNotFoundException("InventoryLot not found with id " + id);
        }
        lotRepo.deleteById(id);
    }

    private InventoryLotDTO toDto(InventoryLot lot) {
        return InventoryLotDTO.builder()
                .id(lot.getId())
                .productId(lot.getInvLotProd().getId())
                .lotNumber(lot.getInvLotNumber())
                .expirationDate(lot.getInvLotExpirationDate())
                .quantity(lot.getInvLotQuantity())
                .warehouseId(lot.getInvLotWhs() != null ? lot.getInvLotWhs().getId() : null)
                .createdAt(lot.getInvLotCreatedAt())
                .build();
    }
}