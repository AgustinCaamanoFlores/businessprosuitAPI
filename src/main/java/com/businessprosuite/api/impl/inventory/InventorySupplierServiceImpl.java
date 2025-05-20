package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventorySupplierDTO;
import com.businessprosuite.api.model.inventory.InventorySupplier;
import com.businessprosuite.api.repository.inventory.InventorySupplierRepository;
import com.businessprosuite.api.service.inventory.InventorySupplierService;
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
public class InventorySupplierServiceImpl implements InventorySupplierService {

    private final InventorySupplierRepository repo;

    @Override
    public List<InventorySupplierDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventorySupplierDTO findById(Integer id) {
        InventorySupplier entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventorySupplier not found with id " + id));
        return toDto(entity);
    }

    @Override
    public InventorySupplierDTO create(InventorySupplierDTO dto) {
        InventorySupplier supplier = new InventorySupplier();
        supplier.setInvSupName(dto.getName());
        supplier.setInvSupEmail(dto.getEmail());
        supplier.setInvSupPhone(dto.getPhone());
        supplier.setInvSupAddress(dto.getAddress());
        supplier.setInvSupCreatedAt(LocalDateTime.now());
        supplier.setInvSupUpdatedAt(LocalDateTime.now());
        InventorySupplier saved = repo.save(supplier);
        return toDto(saved);
    }

    @Override
    public InventorySupplierDTO update(Integer id, InventorySupplierDTO dto) {
        InventorySupplier supplier = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventorySupplier not found with id " + id));
        supplier.setInvSupName(dto.getName());
        supplier.setInvSupEmail(dto.getEmail());
        supplier.setInvSupPhone(dto.getPhone());
        supplier.setInvSupAddress(dto.getAddress());
        supplier.setInvSupUpdatedAt(LocalDateTime.now());
        InventorySupplier updated = repo.save(supplier);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("InventorySupplier not found with id " + id);
        }
        repo.deleteById(id);
    }

    private InventorySupplierDTO toDto(InventorySupplier entity) {
        return InventorySupplierDTO.builder()
                .id(entity.getId())
                .name(entity.getInvSupName())
                .email(entity.getInvSupEmail())
                .phone(entity.getInvSupPhone())
                .address(entity.getInvSupAddress())
                .createdAt(entity.getInvSupCreatedAt())
                .updatedAt(entity.getInvSupUpdatedAt())
                .build();
    }
}
