// src/main/java/com/businessprosuite/api/service/impl/company/BranchServiceImpl.java
package com.businessprosuite.api.impl.company;

import com.businessprosuite.api.dto.company.CompanyBranchDTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.company.CompanyBranch;
import com.businessprosuite.api.repository.company.CompanyBranchRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.company.CompanyBranchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyBranchServiceImpl implements CompanyBranchService {

    private final CompanyBranchRepository repo;
    private final CompanyRepository companyRepo;

    public CompanyBranchServiceImpl(CompanyBranchRepository repo,
                                    CompanyRepository companyRepo) {
        this.repo = repo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<CompanyBranchDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyBranchDTO findById(Integer id) {
        CompanyBranch entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Branch no encontrado: " + id));
        return toDto(entity);
    }

    @Override
    public CompanyBranchDTO create(CompanyBranchDTO dto) {
        CompanyBranch entity = toEntity(dto);
        CompanyBranch saved = repo.save(entity);
        return toDto(saved);
    }

    @Override
    public CompanyBranchDTO update(Integer id, CompanyBranchDTO dto) {
        CompanyBranch existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Branch no encontrado: " + id));

        existing.setBrcName(dto.getName());
        existing.setBrcAddress(dto.getAddress());
        existing.setBrcPhone(dto.getPhone());
        // Nota: la compañía no se actualiza para preservar la integridad referencial
        CompanyBranch updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private CompanyBranchDTO toDto(CompanyBranch e) {
        return CompanyBranchDTO.builder()
                .id(e.getId())
                .companyId(e.getBrcCmp().getId())
                .name(e.getBrcName())
                .address(e.getBrcAddress())
                .phone(e.getBrcPhone())
                .createdAt(e.getBrcCreatedAt())
                .updatedAt(e.getBrcUpdatedAt())
                .build();
    }

    private CompanyBranch toEntity(CompanyBranchDTO d) {
        CompanyBranch e = new CompanyBranch();
        Company cmp = companyRepo.findById(d.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Company no encontrado: " + d.getCompanyId()));
        e.setBrcCmp(cmp);
        e.setBrcName(d.getName());
        e.setBrcAddress(d.getAddress());
        e.setBrcPhone(d.getPhone());
        // createdAt/updatedAt se manejan por defecto en BD
        return e;
    }
}
