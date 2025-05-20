package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.FinanceCOADTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.finance.FinanceCOA;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.finance.FinanceCOARepository;
import com.businessprosuite.api.service.finance.FinanceCOAService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FinanceCOAServiceImpl implements FinanceCOAService {

    private final FinanceCOARepository coaRepo;
    private final CompanyRepository    companyRepo;

    @Override
    public List<FinanceCOADTO> findAll() {
        return coaRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FinanceCOADTO findById(Integer id) {
        FinanceCOA coa = coaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FinanceCOA not found with id " + id));
        return toDto(coa);
    }

    @Override
    public FinanceCOADTO create(FinanceCOADTO dto) {
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        FinanceCOA coa = new FinanceCOA();
        coa.setFinCoaCode(dto.getCode());
        coa.setFinCoaName(dto.getName());
        coa.setFinCoaType(dto.getType());
        coa.setFinCoaDescription(dto.getDescription());
        coa.setFinCoaCmp(company);
        coa.setFinCoaCreatedAt(LocalDateTime.now());
        coa.setFinCoaUpdatedAt(LocalDateTime.now());

        FinanceCOA saved = coaRepo.save(coa);
        return toDto(saved);
    }

    @Override
    public FinanceCOADTO update(Integer id, FinanceCOADTO dto) {
        FinanceCOA coa = coaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FinanceCOA not found with id " + id));
        Company company = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        coa.setFinCoaCode(dto.getCode());
        coa.setFinCoaName(dto.getName());
        coa.setFinCoaType(dto.getType());
        coa.setFinCoaDescription(dto.getDescription());
        coa.setFinCoaCmp(company);
        coa.setFinCoaUpdatedAt(LocalDateTime.now());

        FinanceCOA updated = coaRepo.save(coa);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!coaRepo.existsById(id)) {
            throw new EntityNotFoundException("FinanceCOA not found with id " + id);
        }
        coaRepo.deleteById(id);
    }

    private FinanceCOADTO toDto(FinanceCOA coa) {
        return FinanceCOADTO.builder()
                .id(coa.getId())
                .code(coa.getFinCoaCode())
                .name(coa.getFinCoaName())
                .type(coa.getFinCoaType())
                .description(coa.getFinCoaDescription())
                .companyId(coa.getFinCoaCmp().getId())
                .createdAt(coa.getFinCoaCreatedAt())
                .updatedAt(coa.getFinCoaUpdatedAt())
                .build();
    }
}