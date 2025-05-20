package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityPasswordPolicyDTO;
import java.util.List;

public interface SecurityPasswordPolicyService {
    List<SecurityPasswordPolicyDTO> findAll();
    SecurityPasswordPolicyDTO findById(Integer id);
    SecurityPasswordPolicyDTO create(SecurityPasswordPolicyDTO dto);
    SecurityPasswordPolicyDTO update(Integer id, SecurityPasswordPolicyDTO dto);
    void delete(Integer id);
}
