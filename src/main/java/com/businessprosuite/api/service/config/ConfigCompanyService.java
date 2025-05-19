package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigCompanyDTO;
import java.util.List;

public interface ConfigCompanyService {
    List<ConfigCompanyDTO> findAll();
    ConfigCompanyDTO findById(Integer id);
    ConfigCompanyDTO create(ConfigCompanyDTO dto);
    ConfigCompanyDTO update(Integer id, ConfigCompanyDTO dto);
    void delete(Integer id);
}
