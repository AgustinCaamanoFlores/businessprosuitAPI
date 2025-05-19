package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigSectorDTO;
import com.businessprosuite.api.model.config.ConfigSector;
import com.businessprosuite.api.repository.config.ConfigSectorRepository;
import com.businessprosuite.api.service.config.ConfigSectorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigSectorServiceImpl implements ConfigSectorService {

    private final ConfigSectorRepository repo;

    public ConfigSectorServiceImpl(ConfigSectorRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ConfigSectorDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigSectorDTO findById(Integer id) {
        ConfigSector e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigSector no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigSectorDTO create(ConfigSectorDTO dto) {
        ConfigSector e = toEntity(dto);
        ConfigSector saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigSectorDTO update(Integer id, ConfigSectorDTO dto) {
        ConfigSector existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigSector no encontrado: " + id));
        existing.setCfgSectorNombre(dto.getName());
        ConfigSector updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ConfigSectorDTO toDto(ConfigSector e) {
        return ConfigSectorDTO.builder()
                .id(e.getId())
                .name(e.getCfgSectorNombre())
                .build();
    }

    private ConfigSector toEntity(ConfigSectorDTO d) {
        ConfigSector e = new ConfigSector();
        e.setId(d.getId());
        e.setCfgSectorNombre(d.getName());
        return e;
    }
}
