package com.businessprosuite.api.impl.subs;

import com.businessprosuite.api.dto.subs.SubsPlanDTO;
import com.businessprosuite.api.model.subs.SubsPlan;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.subs.SubsPlanRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.subs.SubsPlanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubsPlanServiceImpl implements SubsPlanService {

    private final SubsPlanRepository planRepo;
    private final ConfigCompanyRepository companyRepo;

    @Override
    public List<SubsPlanDTO> findAll() {
        return planRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubsPlanDTO findById(Integer id) {
        SubsPlan plan = planRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubsPlan not found with id " + id));
        return toDto(plan);
    }

    @Override
    public SubsPlanDTO create(SubsPlanDTO dto) {
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        SubsPlan plan = new SubsPlan();
        plan.setSubsPlanNombre(dto.getName());
        plan.setSubsPlanPrecio(dto.getPrice());
        plan.setSubsPlanPeriodo(dto.getPeriod());
        plan.setConfigCompany(company);
        SubsPlan saved = planRepo.save(plan);
        return toDto(saved);
    }

    @Override
    public SubsPlanDTO update(Integer id, SubsPlanDTO dto) {
        SubsPlan plan = planRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubsPlan not found with id " + id));
        ConfigCompany company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getCompanyId()));
        plan.setSubsPlanNombre(dto.getName());
        plan.setSubsPlanPrecio(dto.getPrice());
        plan.setSubsPlanPeriodo(dto.getPeriod());
        plan.setConfigCompany(company);
        SubsPlan updated = planRepo.save(plan);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!planRepo.existsById(id)) {
            throw new EntityNotFoundException("SubsPlan not found with id " + id);
        }
        planRepo.deleteById(id);
    }

    private SubsPlanDTO toDto(SubsPlan plan) {
        return SubsPlanDTO.builder()
                .id(plan.getId())
                .name(plan.getSubsPlanNombre())
                .price(plan.getSubsPlanPrecio())
                .period(plan.getSubsPlanPeriodo())
                .companyId(plan.getConfigCompany().getId())
                .build();
    }
}