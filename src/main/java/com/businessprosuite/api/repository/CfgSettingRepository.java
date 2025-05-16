package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.configuraciones.CfgSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgSettingRepository extends JpaRepository<CfgSetting, Integer> {
}