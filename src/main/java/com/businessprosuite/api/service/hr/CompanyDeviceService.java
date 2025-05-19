package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.CompanyDeviceDTO;
import java.util.List;

public interface CompanyDeviceService {
    List<CompanyDeviceDTO> findAll();
    CompanyDeviceDTO findById(Integer id);
    CompanyDeviceDTO create(CompanyDeviceDTO dto);
    CompanyDeviceDTO update(Integer id, CompanyDeviceDTO dto);
    void delete(Integer id);
}
