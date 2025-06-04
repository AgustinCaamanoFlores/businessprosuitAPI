// src/main/java/com/businessprosuite/api/service/impl/company/CompanyServiceImpl.java
package com.businessprosuite.api.impl.company;

import com.businessprosuite.api.dto.company.CompanyDTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.config.ConfigCountryRepository;
import com.businessprosuite.api.service.company.CompanyService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repo;
    private final ConfigCompanyRepository configCmpRepo;
    private final ConfigCountryRepository countryRepo;

    public CompanyServiceImpl(CompanyRepository repo,
                              ConfigCompanyRepository configCmpRepo,
                              ConfigCountryRepository countryRepo) {
        this.repo = repo;
        this.configCmpRepo = configCmpRepo;
        this.countryRepo = countryRepo;
    }

    @Override
    @Cacheable("companies")
    public List<CompanyDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO findById(Integer id) {
        Company e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public CompanyDTO create(CompanyDTO dto) {
        Company e = toEntity(dto);
        Company saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public CompanyDTO update(Integer id, CompanyDTO dto) {
        Company existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company no encontrado: " + id));

        existing.setCmpName(dto.getName());
        existing.setCmpAddress(dto.getAddress());
        existing.setCmpPhone(dto.getPhone());
        existing.setCmpEmail(dto.getEmail());
        existing.setCmpTaxId(dto.getTaxId());
        // actualizar país si cambió
        if (!existing.getCmpConfigCountryCodigo().getCodigo().equals(dto.getCountryCode())) {
            ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Country no encontrado: " + dto.getCountryCode()));
            existing.setCmpConfigCountryCodigo(country);
        }
        Company updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private CompanyDTO toDto(Company e) {
        return CompanyDTO.builder()
                .id(e.getId())
                .configCompanyId(e.getConfigCompany().getId())
                .name(e.getCmpName())
                .address(e.getCmpAddress())
                .phone(e.getCmpPhone())
                .email(e.getCmpEmail())
                .taxId(e.getCmpTaxId())
                .countryCode(e.getCmpConfigCountryCodigo().getCodigo())
                .createdAt(e.getCmpCreatedAt())
                .updatedAt(e.getCmpUpdatedAt())
                .build();
    }

    private Company toEntity(CompanyDTO d) {
        Company e = new Company();
        ConfigCompany cfg = configCmpRepo.findById(d.getConfigCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrado: " + d.getConfigCompanyId()));
        ConfigCountry country = countryRepo.findById(d.getCountryCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country no encontrado: " + d.getCountryCode()));

        e.setConfigCompany(cfg);
        e.setCmpName(d.getName());
        e.setCmpAddress(d.getAddress());
        e.setCmpPhone(d.getPhone());
        e.setCmpEmail(d.getEmail());
        e.setCmpTaxId(d.getTaxId());
        e.setCmpConfigCountryCodigo(country);
        // createdAt/updatedAt se asignan por defecto en BD
        return e;
    }
}
