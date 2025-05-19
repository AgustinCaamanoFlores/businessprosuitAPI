package com.businessprosuite.api.repository.asset;

import com.businessprosuite.api.model.asset.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer> {
}