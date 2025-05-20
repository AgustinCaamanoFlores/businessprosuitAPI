package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityPermissionDTO;
import java.util.List;

public interface SecurityPermissionService {
    List<SecurityPermissionDTO> findAll();
    SecurityPermissionDTO findById(Integer id);
    SecurityPermissionDTO create(SecurityPermissionDTO dto);
    SecurityPermissionDTO update(Integer id, SecurityPermissionDTO dto);
    void delete(Integer id);
}