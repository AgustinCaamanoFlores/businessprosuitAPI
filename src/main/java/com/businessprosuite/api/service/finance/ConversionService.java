package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.ConversionDTO;

import java.util.List;

public interface ConversionService {
    List<ConversionDTO> findAll();
    ConversionDTO findById(Integer id);
    ConversionDTO create(ConversionDTO dto);
    ConversionDTO update(Integer id, ConversionDTO dto);
    void delete(Integer id);
}