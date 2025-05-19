package com.businessprosuite.api.repository.asset;

import com.businessprosuite.api.model.asset.AssetMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetMaintenanceRepository extends JpaRepository<AssetMaintenance, Integer> {
}