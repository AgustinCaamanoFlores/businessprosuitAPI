package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecuritySessionDTO;
import java.util.List;

public interface SecuritySessionService {
    List<SecuritySessionDTO> findAll();
    SecuritySessionDTO findById(String sessId);
    SecuritySessionDTO create(SecuritySessionDTO dto);
    SecuritySessionDTO update(String sessId, SecuritySessionDTO dto);
    void delete(String sessId);
}