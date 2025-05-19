package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigModuleParametersDTO;
import com.businessprosuite.api.model.config.ConfigModuleParameters;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.config.ConfigModuleParametersRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.config.ConfigModuleParametersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigModuleParametersServiceImpl implements ConfigModuleParametersService {

    private final ConfigModuleParametersRepository repo;
    private final ConfigCompanyRepository          companyRepo;

    public ConfigModuleParametersServiceImpl(
            ConfigModuleParametersRepository repo,
            ConfigCompanyRepository companyRepo) {
        this.repo = repo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<ConfigModuleParametersDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigModuleParametersDTO findById(Integer id) {
        ConfigModuleParameters e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigModuleParameters no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigModuleParametersDTO create(ConfigModuleParametersDTO dto) {
        ConfigModuleParameters e = toEntity(dto);
        ConfigModuleParameters saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigModuleParametersDTO update(Integer id, ConfigModuleParametersDTO dto) {
        ConfigModuleParameters existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigModuleParameters no encontrado: " + id));
        existing.setModuleName(dto.getModuleName());
        existing.setParamKey(dto.getParamKey());
        existing.setParamValue(dto.getParamValue());
        // No actualizamos id; si cambia company:
        if (!existing.getConfigCompany().getId().equals(dto.getConfigCompanyId())) {
            ConfigCompany cfg = companyRepo.findById(dto.getConfigCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "ConfigCompany no encontrado: " + dto.getConfigCompanyId()));
            existing.setConfigCompany(cfg);
        }
        ConfigModuleParameters updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ConfigModuleParametersDTO toDto(ConfigModuleParameters e) {
        return ConfigModuleParametersDTO.builder()
                .id(e.getId())
                .moduleName(e.getModuleName())
                .paramKey(e.getParamKey())
                .paramValue(e.getParamValue())
                .configCompanyId(e.getConfigCompany().getId())
                .build();
    }

    private ConfigModuleParameters toEntity(ConfigModuleParametersDTO d) {
        ConfigModuleParameters e = new ConfigModuleParameters();
        e.setModuleName(d.getModuleName());
        e.setParamKey(d.getParamKey());
        e.setParamValue(d.getParamValue());

        ConfigCompany cfg = companyRepo.findById(d.getConfigCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrado: " + d.getConfigCompanyId()));
        e.setConfigCompany(cfg);
        return e;
    }
}
