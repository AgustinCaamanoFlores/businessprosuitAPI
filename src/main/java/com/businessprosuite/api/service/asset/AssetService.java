package com.businessprosuite.api.service.asset;

import com.businessprosuite.api.dto.asset.AssetDTO;
import java.util.List;

public interface AssetService {
    List<AssetDTO> findAll();
    AssetDTO findById(Integer id);
    AssetDTO create(AssetDTO dto);
    AssetDTO update(Integer id, AssetDTO dto);
    void delete(Integer id);
}
