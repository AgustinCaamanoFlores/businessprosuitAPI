package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigCompanyDTO;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.config.ConfigCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigCompanyServiceImpl implements ConfigCompanyService {

    private final ConfigCompanyRepository repo;

    public ConfigCompanyServiceImpl(ConfigCompanyRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ConfigCompanyDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigCompanyDTO findById(Integer id) {
        ConfigCompany e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigCompanyDTO create(ConfigCompanyDTO dto) {
        ConfigCompany e = toEntity(dto);
        ConfigCompany saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigCompanyDTO update(Integer id, ConfigCompanyDTO dto) {
        ConfigCompany existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrado: " + id));

        existing.setEmpNombre(dto.getNombre());
        existing.setEmpMonedaBase(dto.getMonedaBase());
        existing.setEmpRegion(dto.getRegion());
        existing.setEmpConfig(dto.getConfigJson());
        // creadoAt se gestiona desde BD; updatedAt automático por Hibernate si lo configuras
        ConfigCompany updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ConfigCompanyDTO toDto(ConfigCompany e) {
        return ConfigCompanyDTO.builder()
                .id(e.getId())
                .nombre(e.getEmpNombre())
                .monedaBase(e.getEmpMonedaBase())
                .region(e.getEmpRegion())
                .configJson(e.getEmpConfig())
                .creadoAt(e.getEmpCreadoAt())
                .build();
    }

    private ConfigCompany toEntity(ConfigCompanyDTO d) {
        ConfigCompany e = new ConfigCompany();
        e.setEmpNombre(d.getNombre());
        e.setEmpMonedaBase(d.getMonedaBase());
        e.setEmpRegion(d.getRegion());
        e.setEmpConfig(d.getConfigJson());
        // empCreadoAt lo deja la BD
        return e;
    }
}
