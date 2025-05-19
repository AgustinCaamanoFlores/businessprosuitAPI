package com.businessprosuite.api.impl.asset;

import com.businessprosuite.api.dto.asset.AssetCategoryDTO;
import com.businessprosuite.api.model.asset.AssetCategory;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.asset.AssetCategoryRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.asset.AssetCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetCategoryServiceImpl implements AssetCategoryService {

    private final AssetCategoryRepository repo;
    private final ConfigCompanyRepository companyRepo;

    public AssetCategoryServiceImpl(AssetCategoryRepository repo,
                                    ConfigCompanyRepository companyRepo) {
        this.repo = repo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<AssetCategoryDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssetCategoryDTO findById(Integer id) {
        AssetCategory e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AssetCategory no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public AssetCategoryDTO create(AssetCategoryDTO dto) {
        AssetCategory e = toEntity(dto);
        AssetCategory saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public AssetCategoryDTO update(Integer id, AssetCategoryDTO dto) {
        AssetCategory existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AssetCategory no encontrado: " + id));
        existing.setActCatNombre(dto.getName());
        if (!existing.getConfigCompany().getId().equals(dto.getCompanyId())) {
            ConfigCompany cfg = companyRepo.findById(dto.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "ConfigCompany no encontrada: " + dto.getCompanyId()));
            existing.setConfigCompany(cfg);
        }
        AssetCategory updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private AssetCategoryDTO toDto(AssetCategory e) {
        return AssetCategoryDTO.builder()
                .id(e.getId())
                .name(e.getActCatNombre())
                .companyId(e.getConfigCompany().getId())
                .build();
    }

    private AssetCategory toEntity(AssetCategoryDTO d) {
        AssetCategory e = new AssetCategory();
        e.setActCatNombre(d.getName());
        ConfigCompany cfg = companyRepo.findById(d.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrada: " + d.getCompanyId()));
        e.setConfigCompany(cfg);
        return e;
    }
}
