package com.businessprosuite.api.service.audit;

import com.businessprosuite.api.dto.audit.SensitiveDataAccessDTO;
import java.util.List;

public interface SensitiveDataAccessService {
    List<SensitiveDataAccessDTO> findAll();
    SensitiveDataAccessDTO findById(Integer id);
    SensitiveDataAccessDTO create(SensitiveDataAccessDTO dto);
    SensitiveDataAccessDTO update(Integer id, SensitiveDataAccessDTO dto);
    void delete(Integer id);
}