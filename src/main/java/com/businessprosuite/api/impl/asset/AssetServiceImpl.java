package com.businessprosuite.api.impl.asset;

import com.businessprosuite.api.dto.asset.AssetDTO;
import com.businessprosuite.api.model.asset.Asset;
import com.businessprosuite.api.model.asset.AssetCategory;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.asset.AssetRepository;
import com.businessprosuite.api.repository.asset.AssetCategoryRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.asset.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepo;
    private final AssetCategoryRepository categoryRepo;
    private final ConfigCompanyRepository companyRepo;

    public AssetServiceImpl(AssetRepository assetRepo,
                            AssetCategoryRepository categoryRepo,
                            ConfigCompanyRepository companyRepo) {
        this.assetRepo = assetRepo;
        this.categoryRepo = categoryRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<AssetDTO> findAll() {
        return assetRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssetDTO findById(Integer id) {
        Asset e = assetRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public AssetDTO create(AssetDTO dto) {
        Asset e = toEntity(dto);
        Asset saved = assetRepo.save(e);
        return toDto(saved);
    }

    @Override
    public AssetDTO update(Integer id, AssetDTO dto) {
        Asset existing = assetRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset no encontrado: " + id));
        existing.setActCodInterno(dto.getInternalCode());
        existing.setActDescripcion(dto.getDescription());
        existing.setActValorCompra(dto.getPurchaseValue());
        existing.setActFechaAdq(dto.getAcquisitionDate());

        if (!existing.getActCat().getId().equals(dto.getCategoryId())) {
            AssetCategory cat = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada: " + dto.getCategoryId()));
            existing.setActCat(cat);
        }
        if (!existing.getConfigCompany().getId().equals(dto.getCompanyId())) {
            ConfigCompany cmp = companyRepo.findById(dto.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Company no encontrada: " + dto.getCompanyId()));
            existing.setConfigCompany(cmp);
        }

        Asset updated = assetRepo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        assetRepo.deleteById(id);
    }

    private AssetDTO toDto(Asset e) {
        return AssetDTO.builder()
                .id(e.getId())
                .internalCode(e.getActCodInterno())
                .description(e.getActDescripcion())
                .purchaseValue(e.getActValorCompra())
                .acquisitionDate(e.getActFechaAdq())
                .categoryId(e.getActCat().getId())
                .companyId(e.getConfigCompany().getId())
                .build();
    }

    private Asset toEntity(AssetDTO d) {
        Asset e = new Asset();
        e.setActCodInterno(d.getInternalCode());
        e.setActDescripcion(d.getDescription());
        e.setActValorCompra(d.getPurchaseValue());
        e.setActFechaAdq(d.getAcquisitionDate());

        AssetCategory cat = categoryRepo.findById(d.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada: " + d.getCategoryId()));
        e.setActCat(cat);

        ConfigCompany cmp = companyRepo.findById(d.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company no encontrada: " + d.getCompanyId()));
        e.setConfigCompany(cmp);

        return e;
    }
}
