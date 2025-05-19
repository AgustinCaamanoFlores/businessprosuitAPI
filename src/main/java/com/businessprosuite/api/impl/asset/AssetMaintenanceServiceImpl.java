package com.businessprosuite.api.impl.asset;

import com.businessprosuite.api.dto.asset.AssetMaintenanceDTO;
import com.businessprosuite.api.model.asset.Asset;
import com.businessprosuite.api.model.asset.AssetMaintenance;
import com.businessprosuite.api.repository.asset.AssetMaintenanceRepository;
import com.businessprosuite.api.repository.asset.AssetRepository;
import com.businessprosuite.api.service.asset.AssetMaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetMaintenanceServiceImpl implements AssetMaintenanceService {

    private final AssetMaintenanceRepository repo;
    private final AssetRepository               assetRepo;

    public AssetMaintenanceServiceImpl(AssetMaintenanceRepository repo,
                                       AssetRepository assetRepo) {
        this.repo = repo;
        this.assetRepo = assetRepo;
    }

    @Override
    public List<AssetMaintenanceDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssetMaintenanceDTO findById(Integer id) {
        AssetMaintenance e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AssetMaintenance no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public AssetMaintenanceDTO create(AssetMaintenanceDTO dto) {
        AssetMaintenance e = toEntity(dto);
        AssetMaintenance saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public AssetMaintenanceDTO update(Integer id, AssetMaintenanceDTO dto) {
        AssetMaintenance existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "AssetMaintenance no encontrado: " + id));
        if (!existing.getAct().getId().equals(dto.getAssetId())) {
            Asset a = assetRepo.findById(dto.getAssetId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Asset no encontrado: " + dto.getAssetId()));
            existing.setAct(a);
        }
        existing.setActMantFecha(dto.getMaintenanceDate());
        existing.setActMantDetalle(dto.getDetail());
        AssetMaintenance updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private AssetMaintenanceDTO toDto(AssetMaintenance e) {
        return AssetMaintenanceDTO.builder()
                .id(e.getId())
                .assetId(e.getAct().getId())
                .maintenanceDate(e.getActMantFecha())
                .detail(e.getActMantDetalle())
                .build();
    }

    private AssetMaintenance toEntity(AssetMaintenanceDTO d) {
        AssetMaintenance e = new AssetMaintenance();
        Asset a = assetRepo.findById(d.getAssetId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Asset no encontrado: " + d.getAssetId()));
        e.setAct(a);
        e.setActMantFecha(d.getMaintenanceDate());
        e.setActMantDetalle(d.getDetail());
        return e;
    }
}
