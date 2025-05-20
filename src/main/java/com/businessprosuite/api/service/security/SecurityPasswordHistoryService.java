package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityPasswordHistoryDTO;
import java.util.List;

public interface SecurityPasswordHistoryService {
    List<SecurityPasswordHistoryDTO> findAll();
    SecurityPasswordHistoryDTO findById(Integer id);
    SecurityPasswordHistoryDTO create(SecurityPasswordHistoryDTO dto);
    SecurityPasswordHistoryDTO update(Integer id, SecurityPasswordHistoryDTO dto);
    void delete(Integer id);
}