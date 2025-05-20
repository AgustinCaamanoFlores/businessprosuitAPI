package com.businessprosuite.api.service.security;

import com.businessprosuite.api.dto.security.SecurityPolicyRuleDTO;
import java.util.List;

public interface SecurityPolicyRuleService {
    List<SecurityPolicyRuleDTO> findAll();
    SecurityPolicyRuleDTO findById(Integer id);
    SecurityPolicyRuleDTO create(SecurityPolicyRuleDTO dto);
    SecurityPolicyRuleDTO update(Integer id, SecurityPolicyRuleDTO dto);
    void delete(Integer id);
}