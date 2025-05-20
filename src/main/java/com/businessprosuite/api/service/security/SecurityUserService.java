package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityUserDTO;
import java.util.List;

public interface SecurityUserService {
    List<SecurityUserDTO> findAll();
    SecurityUserDTO findById(Integer id);
    SecurityUserDTO create(SecurityUserDTO dto);
    SecurityUserDTO update(Integer id, SecurityUserDTO dto);
    void delete(Integer id);
}