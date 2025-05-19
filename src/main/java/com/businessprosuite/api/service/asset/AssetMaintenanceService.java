package com.businessprosuite.api.service.asset;

import com.businessprosuite.api.dto.asset.AssetMaintenanceDTO;
import java.util.List;

public interface AssetMaintenanceService {
    List<AssetMaintenanceDTO> findAll();
    AssetMaintenanceDTO findById(Integer id);
    AssetMaintenanceDTO create(AssetMaintenanceDTO dto);
    AssetMaintenanceDTO update(Integer id, AssetMaintenanceDTO dto);
    void delete(Integer id);
}
