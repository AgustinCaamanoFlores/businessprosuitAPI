package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityAttributeDTO;
import java.util.List;

public interface SecurityAttributeService {
    List<SecurityAttributeDTO> findAll();
    SecurityAttributeDTO findById(Integer id);
    SecurityAttributeDTO create(SecurityAttributeDTO dto);
    SecurityAttributeDTO update(Integer id, SecurityAttributeDTO dto);
    void delete(Integer id);
}