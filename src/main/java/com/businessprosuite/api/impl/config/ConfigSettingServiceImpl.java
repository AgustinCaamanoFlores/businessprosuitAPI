package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigSettingDTO;
import com.businessprosuite.api.model.config.ConfigSetting;
import com.businessprosuite.api.repository.config.ConfigSettingRepository;
import com.businessprosuite.api.service.config.ConfigSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigSettingServiceImpl implements ConfigSettingService {

    private final ConfigSettingRepository repo;

    public ConfigSettingServiceImpl(ConfigSettingRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ConfigSettingDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigSettingDTO findById(Integer id) {
        ConfigSetting e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigSetting no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigSettingDTO create(ConfigSettingDTO dto) {
        ConfigSetting e = toEntity(dto);
        ConfigSetting saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigSettingDTO update(Integer id, ConfigSettingDTO dto) {
        ConfigSetting existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigSetting no encontrado: " + id));
        existing.setCfgsetKey(dto.getKey());
        existing.setCfgsetValue(dto.getValue());
        ConfigSetting updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ConfigSettingDTO toDto(ConfigSetting e) {
        return ConfigSettingDTO.builder()
                .id(e.getId())
                .key(e.getCfgsetKey())
                .value(e.getCfgsetValue())
                .createdAt(e.getCfgsetCreatedAt())
                .updatedAt(e.getCfgsetUpdatedAt())
                .build();
    }

    private ConfigSetting toEntity(ConfigSettingDTO d) {
        ConfigSetting e = new ConfigSetting();
        e.setCfgsetKey(d.getKey());
        e.setCfgsetValue(d.getValue());
        // createdAt/updatedAt se gestionan en BD
        return e;
    }
}
