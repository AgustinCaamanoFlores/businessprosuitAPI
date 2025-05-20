package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.FiscalRuleDTO;

import java.util.List;

public interface FiscalRuleService {
    List<FiscalRuleDTO> findAll();
    FiscalRuleDTO findById(Integer id);
    FiscalRuleDTO create(FiscalRuleDTO dto);
    FiscalRuleDTO update(Integer id, FiscalRuleDTO dto);
    void delete(Integer id);
}