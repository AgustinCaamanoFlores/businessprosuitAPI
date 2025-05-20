package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.CurrencyDTO;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDTO> findAll();
    CurrencyDTO findById(Integer id);
    CurrencyDTO create(CurrencyDTO dto);
    CurrencyDTO update(Integer id, CurrencyDTO dto);
    void delete(Integer id);
}