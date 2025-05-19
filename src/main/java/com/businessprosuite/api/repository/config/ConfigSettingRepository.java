package com.businessprosuite.api.repository.config;

import com.businessprosuite.api.model.config.ConfigSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigSettingRepository extends JpaRepository<ConfigSetting, Integer> {
}