package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigSectorDTO;
import java.util.List;

public interface ConfigSectorService {
    List<ConfigSectorDTO> findAll();
    ConfigSectorDTO findById(Integer id);
    ConfigSectorDTO create(ConfigSectorDTO dto);
    ConfigSectorDTO update(Integer id, ConfigSectorDTO dto);
    void delete(Integer id);
}
