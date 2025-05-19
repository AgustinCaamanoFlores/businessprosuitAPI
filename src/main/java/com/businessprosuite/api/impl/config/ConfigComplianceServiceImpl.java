package com.businessprosuite.api.impl.config;

import com.businessprosuite.api.dto.config.ConfigComplianceDTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.config.ConfigCompliance;
import com.businessprosuite.api.model.config.ConfigComplianceId;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.config.ConfigComplianceRepository;
import com.businessprosuite.api.service.config.ConfigComplianceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigComplianceServiceImpl implements ConfigComplianceService {

    private final ConfigComplianceRepository repo;
    private final CompanyRepository companyRepo;

    public ConfigComplianceServiceImpl(
            ConfigComplianceRepository repo,
            CompanyRepository companyRepo) {
        this.repo = repo;
        this.companyRepo = companyRepo;
    }
    @Override
    public List<ConfigComplianceDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigComplianceDTO findById(Integer companyId, String complianceCode) {
        ConfigComplianceId id = new ConfigComplianceId(companyId, complianceCode);
        ConfigCompliance e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompliance no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ConfigComplianceDTO create(ConfigComplianceDTO dto) {
        ConfigCompliance e = toEntity(dto);
        ConfigCompliance saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ConfigComplianceDTO update(Integer companyId,
                                      String complianceCode,
                                      ConfigComplianceDTO dto) {
        ConfigComplianceId id = new ConfigComplianceId(companyId, complianceCode);
        ConfigCompliance existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompliance no encontrado: " + id));
        existing.setCfgComplianceMandatory(dto.getMandatory());
        ConfigCompliance updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer companyId, String complianceCode) {
        ConfigComplianceId id = new ConfigComplianceId(companyId, complianceCode);
        repo.deleteById(id);
    }

    private ConfigComplianceDTO toDto(ConfigCompliance e) {
        ConfigComplianceId id = e.getId();
        return ConfigComplianceDTO.builder()
                .companyId(id.getCfgComplianceCmpId())
                .complianceCode(id.getCfgComplianceStandard())
                .mandatory(e.getCfgComplianceMandatory())
                .build();
    }

    private ConfigCompliance toEntity(ConfigComplianceDTO d) {
        ConfigCompliance e = new ConfigCompliance();

        // 1) Crea el ID compuesto
        ConfigComplianceId id = new ConfigComplianceId(
                d.getCompanyId(),
                d.getComplianceCode()
        );
        e.setId(id);

        // 2) Recupera la entidad Company desde el repositorio
        Company cmp = companyRepo.findById(d.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Company no encontrada: " + d.getCompanyId()));
        e.setCfgComplianceCmp(cmp);

        // 3) Asigna el campo restante
        e.setCfgComplianceMandatory(d.getMandatory());
        return e;
    }
}
