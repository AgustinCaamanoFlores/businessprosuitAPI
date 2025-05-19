package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigCountryDTO;
import java.util.List;

public interface ConfigCountryService {
    List<ConfigCountryDTO> findAll();
    ConfigCountryDTO findByCode(String code);
    ConfigCountryDTO create(ConfigCountryDTO dto);
    ConfigCountryDTO update(String code, ConfigCountryDTO dto);
    void delete(String code);
}
