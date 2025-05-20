package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityUserRoleDTO;
import java.util.List;

public interface SecurityUserRoleService {
    List<SecurityUserRoleDTO> findAll();
    SecurityUserRoleDTO findById(Integer id);
    SecurityUserRoleDTO create(SecurityUserRoleDTO dto);
    SecurityUserRoleDTO update(Integer id, SecurityUserRoleDTO dto);
    void delete(Integer id);
}