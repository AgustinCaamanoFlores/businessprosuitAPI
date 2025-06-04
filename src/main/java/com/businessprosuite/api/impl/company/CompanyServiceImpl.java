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
import com.businessprosuite.api.mapper.CompanyMapper;
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
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository repo,
                              ConfigCompanyRepository configCmpRepo,
                              ConfigCountryRepository countryRepo,
                              CompanyMapper companyMapper) {
        this.repo = repo;
        this.configCmpRepo = configCmpRepo;
        this.countryRepo = countryRepo;
        this.companyMapper = companyMapper;
    }

    @Override
    @Cacheable("companies")
    public List<CompanyDTO> findAll() {
        return repo.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO findById(Integer id) {
        Company e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company no encontrado: " + id));
        return companyMapper.toDto(e);
    }

    @Override
    public CompanyDTO create(CompanyDTO dto) {
        Company e = companyMapper.toEntity(dto);
        ConfigCompany cfg = configCmpRepo.findById(dto.getConfigCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConfigCompany no encontrado: " + dto.getConfigCompanyId()));
        ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country no encontrado: " + dto.getCountryCode()));
        e.setConfigCompany(cfg);
        e.setCmpConfigCountryCodigo(country);
        Company saved = repo.save(e);
        return companyMapper.toDto(saved);
    }

    @Override
    public CompanyDTO update(Integer id, CompanyDTO dto) {
        Company existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company no encontrado: " + id));

        companyMapper.updateEntity(dto, existing);
        // actualizar país si cambió
        if (!existing.getCmpConfigCountryCodigo().getCodigo().equals(dto.getCountryCode())) {
            ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Country no encontrado: " + dto.getCountryCode()));
            existing.setCmpConfigCountryCodigo(country);
        }
        Company updated = repo.save(existing);
        return companyMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
