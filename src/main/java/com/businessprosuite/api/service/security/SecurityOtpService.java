package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityOtpDTO;
import java.util.List;

public interface SecurityOtpService {
    List<SecurityOtpDTO> findAll();
    SecurityOtpDTO findById(Integer id);
    SecurityOtpDTO create(SecurityOtpDTO dto);
    SecurityOtpDTO update(Integer id, SecurityOtpDTO dto);
    void delete(Integer id);
}