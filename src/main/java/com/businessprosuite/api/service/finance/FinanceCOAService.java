package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.FinanceCOADTO;

import java.util.List;

public interface FinanceCOAService {
    List<FinanceCOADTO> findAll();
    FinanceCOADTO findById(Integer id);
    FinanceCOADTO create(FinanceCOADTO dto);
    FinanceCOADTO update(Integer id, FinanceCOADTO dto);
    void delete(Integer id);
}
