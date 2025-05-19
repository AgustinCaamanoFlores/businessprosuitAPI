// src/main/java/com/businessprosuite/api/service/company/BranchService.java
package com.businessprosuite.api.service.company;

import com.businessprosuite.api.dto.company.CompanyBranchDTO;
import java.util.List;

public interface CompanyBranchService {
    List<CompanyBranchDTO> findAll();
    CompanyBranchDTO findById(Integer id);
    CompanyBranchDTO create(CompanyBranchDTO dto);
    CompanyBranchDTO update(Integer id, CompanyBranchDTO dto);
    void delete(Integer id);
}
