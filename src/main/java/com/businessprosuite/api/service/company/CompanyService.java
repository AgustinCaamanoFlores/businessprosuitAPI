// src/main/java/com/businessprosuite/api/service/company/CompanyService.java
package com.businessprosuite.api.service.company;

import com.businessprosuite.api.dto.company.CompanyDTO;
import java.util.List;

public interface CompanyService {
    List<CompanyDTO> findAll();
    CompanyDTO findById(Integer id);
    CompanyDTO create(CompanyDTO dto);
    CompanyDTO update(Integer id, CompanyDTO dto);
    void delete(Integer id);
}
