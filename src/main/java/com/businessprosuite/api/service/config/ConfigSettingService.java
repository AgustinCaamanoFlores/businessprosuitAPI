package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigSettingDTO;
import java.util.List;

public interface ConfigSettingService {
    List<ConfigSettingDTO> findAll();
    ConfigSettingDTO findById(Integer id);
    ConfigSettingDTO create(ConfigSettingDTO dto);
    ConfigSettingDTO update(Integer id, ConfigSettingDTO dto);
    void delete(Integer id);
}
