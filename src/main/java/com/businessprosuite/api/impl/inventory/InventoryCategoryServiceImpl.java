package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryCategoryDTO;
import com.businessprosuite.api.model.inventory.InventoryCategory;
import com.businessprosuite.api.repository.inventory.InventoryCategoryRepository;
import com.businessprosuite.api.service.inventory.InventoryCategoryService;
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
public class InventoryCategoryServiceImpl implements InventoryCategoryService {

    private final InventoryCategoryRepository repo;

    @Override
    public List<InventoryCategoryDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryCategoryDTO findById(Integer id) {
        InventoryCategory entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryCategory not found with id " + id));
        return toDto(entity);
    }

    @Override
    public InventoryCategoryDTO create(InventoryCategoryDTO dto) {
        InventoryCategory category = new InventoryCategory();
        category.setInvCatName(dto.getName());
        category.setInvCatDescription(dto.getDescription());
        if (dto.getParentId() != null) {
            InventoryCategory parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent InventoryCategory not found with id " + dto.getParentId()));
            category.setInventoryCategoryParent(parent);
        }
        category.setInvCatCreatedAt(LocalDateTime.now());
        category.setInvCatUpdatedAt(LocalDateTime.now());
        InventoryCategory saved = repo.save(category);
        return toDto(saved);
    }

    @Override
    public InventoryCategoryDTO update(Integer id, InventoryCategoryDTO dto) {
        InventoryCategory category = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryCategory not found with id " + id));
        category.setInvCatName(dto.getName());
        category.setInvCatDescription(dto.getDescription());
        if (dto.getParentId() != null) {
            InventoryCategory parent = repo.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent InventoryCategory not found with id " + dto.getParentId()));
            category.setInventoryCategoryParent(parent);
        } else {
            category.setInventoryCategoryParent(null);
        }
        category.setInvCatUpdatedAt(LocalDateTime.now());
        InventoryCategory updated = repo.save(category);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("InventoryCategory not found with id " + id);
        }
        repo.deleteById(id);
    }

    private InventoryCategoryDTO toDto(InventoryCategory entity) {
        List<InventoryCategoryDTO> subcats = entity.getCategories().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return InventoryCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getInvCatName())
                .description(entity.getInvCatDescription())
                .parentId(entity.getInventoryCategoryParent() != null ? entity.getInventoryCategoryParent().getId() : null)
                .createdAt(entity.getInvCatCreatedAt())
                .updatedAt(entity.getInvCatUpdatedAt())
                .subcategories(subcats)
                .build();
    }
}