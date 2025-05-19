package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigModuleParametersDTO;
import java.util.List;

public interface ConfigModuleParametersService {
    List<ConfigModuleParametersDTO> findAll();
    ConfigModuleParametersDTO findById(Integer id);
    ConfigModuleParametersDTO create(ConfigModuleParametersDTO dto);
    ConfigModuleParametersDTO update(Integer id, ConfigModuleParametersDTO dto);
    void delete(Integer id);
}
