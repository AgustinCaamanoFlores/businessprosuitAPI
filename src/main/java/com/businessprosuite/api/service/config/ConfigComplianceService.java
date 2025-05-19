package com.businessprosuite.api.service.config;

import com.businessprosuite.api.dto.config.ConfigComplianceDTO;
import java.util.List;

public interface ConfigComplianceService {
    List<ConfigComplianceDTO> findAll();
    ConfigComplianceDTO findById(Integer companyId, String complianceCode);
    ConfigComplianceDTO create(ConfigComplianceDTO dto);
    ConfigComplianceDTO update(Integer companyId, String complianceCode, ConfigComplianceDTO dto);
    void delete(Integer companyId, String complianceCode);
}
