package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.KpiDefDTO;
import com.businessprosuite.api.model.analytics.KpiDef;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.analytics.KpiDefRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.analytics.KpiDefService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KpiDefServiceImpl implements KpiDefService {

    private final KpiDefRepository repo;
    private final ConfigCompanyRepository companyRepo;

    @Override
    @Transactional(readOnly = true)
    public List<KpiDefDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public KpiDefDTO findById(Integer id) {
        KpiDef kpi = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KpiDef not found with id " + id));
        return toDto(kpi);
    }

    @Override
    public KpiDefDTO create(KpiDefDTO dto) {
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        KpiDef kpi = new KpiDef();
        kpi.setKpiNombre(dto.getName());
        kpi.setConfigCompany(company);
        KpiDef saved = repo.save(kpi);
        return toDto(saved);
    }

    @Override
    public KpiDefDTO update(Integer id, KpiDefDTO dto) {
        KpiDef kpi = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("KpiDef not found with id " + id));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        kpi.setKpiNombre(dto.getName());
        kpi.setConfigCompany(company);
        KpiDef updated = repo.save(kpi);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("KpiDef not found with id " + id);
        }
        repo.deleteById(id);
    }

    private KpiDefDTO toDto(KpiDef kpi) {
        return KpiDefDTO.builder()
                .id(kpi.getId())
                .name(kpi.getKpiNombre())
                .companyId(kpi.getConfigCompany().getId())
                .build();
    }
}