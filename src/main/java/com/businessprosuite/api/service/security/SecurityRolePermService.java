package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityRolePermDTO;
import java.util.List;

public interface SecurityRolePermService {
    List<SecurityRolePermDTO> findAll();
    SecurityRolePermDTO findById(Integer id);
    SecurityRolePermDTO create(SecurityRolePermDTO dto);
    SecurityRolePermDTO update(Integer id, SecurityRolePermDTO dto);
    void delete(Integer id);
}