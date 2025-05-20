package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.TaxRateDTO;
import java.util.List;

public interface TaxRateService {
    List<TaxRateDTO> findAll();
    TaxRateDTO findById(Integer id);
    TaxRateDTO create(TaxRateDTO dto);
    TaxRateDTO update(Integer id, TaxRateDTO dto);
    void delete(Integer id);
}
