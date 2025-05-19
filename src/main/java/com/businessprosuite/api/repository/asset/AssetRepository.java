package com.businessprosuite.api.repository.asset;

import com.businessprosuite.api.model.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
}