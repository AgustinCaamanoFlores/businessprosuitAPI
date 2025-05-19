package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigEncryptionKeyDTO;
import com.businessprosuite.api.model.config.ConfigEncryptionKey;
import com.businessprosuite.api.repository.config.ConfigEncryptionKeyRepository;
import com.businessprosuite.api.service.config.ConfigEncryptionKeyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigEncryptionKeyServiceImpl implements ConfigEncryptionKeyService {

    private final ConfigEncryptionKeyRepository repo;

    public ConfigEncryptionKeyServiceImpl(ConfigEncryptionKeyRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ConfigEncryptionKeyDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigEncryptionKeyDTO findById(Integer id) {
        ConfigEncryptionKey e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigEncryptionKey no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigEncryptionKeyDTO create(ConfigEncryptionKeyDTO dto) {
        ConfigEncryptionKey e = toEntity(dto);
        ConfigEncryptionKey saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigEncryptionKeyDTO update(Integer id, ConfigEncryptionKeyDTO dto) {
        ConfigEncryptionKey existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigEncryptionKey no encontrado: " + id));
        existing.setEncryptionKey(dto.getEncryptionKey());
        existing.setIsActive(dto.getActive());
        ConfigEncryptionKey updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ConfigEncryptionKeyDTO toDto(ConfigEncryptionKey e) {
        return ConfigEncryptionKeyDTO.builder()
                .id(e.getId())
                .encryptionKey(e.getEncryptionKey())
                .active(e.getIsActive())
                .createdAt(e.getCreatedAt())
                .build();
    }

    private ConfigEncryptionKey toEntity(ConfigEncryptionKeyDTO d) {
        ConfigEncryptionKey e = new ConfigEncryptionKey();
        e.setEncryptionKey(d.getEncryptionKey());
        e.setIsActive(d.getActive());
        // createdAt lo gestiona la BD por defecto
        return e;
    }
}
