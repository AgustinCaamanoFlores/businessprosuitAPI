package com.businessprosuite.api.service.asset;

import com.businessprosuite.api.dto.asset.AssetCategoryDTO;
import java.util.List;

public interface AssetCategoryService {
    List<AssetCategoryDTO> findAll();
    AssetCategoryDTO findById(Integer id);
    AssetCategoryDTO create(AssetCategoryDTO dto);
    AssetCategoryDTO update(Integer id, AssetCategoryDTO dto);
    void delete(Integer id);
}
