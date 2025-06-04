// src/main/java/com/businessprosuite/api/service/impl/company/BranchServiceImpl.java
package com.businessprosuite.api.impl.company;

import com.businessprosuite.api.dto.company.CompanyBranchDTO;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.company.CompanyBranch;
import com.businessprosuite.api.repository.company.CompanyBranchRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.company.CompanyBranchService;
import com.businessprosuite.api.mapper.CompanyBranchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyBranchServiceImpl implements CompanyBranchService {

    private final CompanyBranchRepository repo;
    private final CompanyRepository companyRepo;
    private final CompanyBranchMapper branchMapper;

    public CompanyBranchServiceImpl(CompanyBranchRepository repo,
                                    CompanyRepository companyRepo,
                                    CompanyBranchMapper branchMapper) {
        this.repo = repo;
        this.companyRepo = companyRepo;
        this.branchMapper = branchMapper;
    }

    @Override
    public List<CompanyBranchDTO> findAll() {
        return repo.findAll().stream()
                .map(branchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyBranchDTO findById(Integer id) {
        CompanyBranch entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Branch no encontrado: " + id));
        return branchMapper.toDto(entity);
    }

    @Override
    public CompanyBranchDTO create(CompanyBranchDTO dto) {
        CompanyBranch entity = branchMapper.toEntity(dto);
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Company no encontrado: " + dto.getCompanyId()));
        entity.setBrcCmp(cmp);
        CompanyBranch saved = repo.save(entity);
        return branchMapper.toDto(saved);
    }

    @Override
    public CompanyBranchDTO update(Integer id, CompanyBranchDTO dto) {
        CompanyBranch existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Branch no encontrado: " + id));

        branchMapper.updateEntity(dto, existing);
        // Nota: la compañía no se actualiza para preservar la integridad referencial
        CompanyBranch updated = repo.save(existing);
        return branchMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
