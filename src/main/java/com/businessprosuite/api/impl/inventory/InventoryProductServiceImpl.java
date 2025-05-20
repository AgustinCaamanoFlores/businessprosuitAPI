package com.businessprosuite.api.impl.inventory;

import com.businessprosuite.api.dto.inventory.InventoryProductDTO;
import com.businessprosuite.api.model.inventory.InventoryProduct;
import com.businessprosuite.api.model.inventory.InventoryCategory;
import com.businessprosuite.api.model.inventory.InventorySupplier;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.inventory.InventoryProductRepository;
import com.businessprosuite.api.repository.inventory.InventoryCategoryRepository;
import com.businessprosuite.api.repository.inventory.InventorySupplierRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.inventory.InventoryProductService;
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
public class InventoryProductServiceImpl implements InventoryProductService {

    private final InventoryProductRepository productRepo;
    private final ConfigCompanyRepository    companyRepo;
    private final InventoryCategoryRepository categoryRepo;
    private final InventorySupplierRepository supplierRepo;

    @Override
    public List<InventoryProductDTO> findAll() {
        return productRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryProductDTO findById(Integer id) {
        InventoryProduct prod = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + id));
        return toDto(prod);
    }

    @Override
    public InventoryProductDTO create(InventoryProductDTO dto) {
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        InventoryCategory category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryCategory not found with id " + dto.getCategoryId()));
        InventorySupplier supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("InventorySupplier not found with id " + dto.getSupplierId()));

        InventoryProduct prod = new InventoryProduct();
        prod.setConfigCompany(company);
        prod.setInvProdName(dto.getName());
        prod.setInvProdDescription(dto.getDescription());
        prod.setInvProdPrice(dto.getPrice());
        prod.setInvProdStock(dto.getStock());
        prod.setInvProdInventoryCategory(category);
        prod.setInvProdSup(supplier);
        prod.setInvProdCreatedAt(LocalDateTime.now());
        prod.setInvProdUpdatedAt(LocalDateTime.now());
        prod.setInvProdReorderLevel(dto.getReorderLevel());

        InventoryProduct saved = productRepo.save(prod);
        return toDto(saved);
    }

    @Override
    public InventoryProductDTO update(Integer id, InventoryProductDTO dto) {
        InventoryProduct prod = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InventoryProduct not found with id " + id));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        InventoryCategory category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("InventoryCategory not found with id " + dto.getCategoryId()));
        InventorySupplier supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("InventorySupplier not found with id " + dto.getSupplierId()));

        prod.setConfigCompany(company);
        prod.setInvProdName(dto.getName());
        prod.setInvProdDescription(dto.getDescription());
        prod.setInvProdPrice(dto.getPrice());
        prod.setInvProdStock(dto.getStock());
        prod.setInvProdInventoryCategory(category);
        prod.setInvProdSup(supplier);
        prod.setInvProdUpdatedAt(LocalDateTime.now());
        prod.setInvProdReorderLevel(dto.getReorderLevel());

        InventoryProduct updated = productRepo.save(prod);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!productRepo.existsById(id)) {
            throw new EntityNotFoundException("InventoryProduct not found with id " + id);
        }
        productRepo.deleteById(id);
    }

    private InventoryProductDTO toDto(InventoryProduct prod) {
        return InventoryProductDTO.builder()
                .id(prod.getId())
                .companyId(prod.getConfigCompany().getId())
                .name(prod.getInvProdName())
                .description(prod.getInvProdDescription())
                .price(prod.getInvProdPrice())
                .stock(prod.getInvProdStock())
                .categoryId(prod.getInvProdInventoryCategory().getId())
                .supplierId(prod.getInvProdSup().getId())
                .createdAt(prod.getInvProdCreatedAt())
                .updatedAt(prod.getInvProdUpdatedAt())
                .reorderLevel(prod.getInvProdReorderLevel())
                .build();
    }
}
