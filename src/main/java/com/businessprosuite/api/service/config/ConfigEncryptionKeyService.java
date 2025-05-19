package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigEncryptionKeyDTO;
import java.util.List;

public interface ConfigEncryptionKeyService {
    List<ConfigEncryptionKeyDTO> findAll();
    ConfigEncryptionKeyDTO findById(Integer id);
    ConfigEncryptionKeyDTO create(ConfigEncryptionKeyDTO dto);
    ConfigEncryptionKeyDTO update(Integer id, ConfigEncryptionKeyDTO dto);
    void delete(Integer id);
}
