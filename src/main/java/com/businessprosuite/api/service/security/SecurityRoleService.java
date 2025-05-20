package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityRoleDTO;
import java.util.List;

public interface SecurityRoleService {
    List<SecurityRoleDTO> findAll();
    SecurityRoleDTO findById(Integer id);
    SecurityRoleDTO create(SecurityRoleDTO dto);
    SecurityRoleDTO update(Integer id, SecurityRoleDTO dto);
    void delete(Integer id);
}