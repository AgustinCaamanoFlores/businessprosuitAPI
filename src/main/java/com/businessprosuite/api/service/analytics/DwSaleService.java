package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.DwSaleDTO;
import com.businessprosuite.api.model.analytics.DwSaleId;
import java.util.List;

public interface DwSaleService {
    List<DwSaleDTO> findAll();
    DwSaleDTO findById(DwSaleId id);
    DwSaleDTO create(DwSaleDTO dto);
    DwSaleDTO update(DwSaleId id, DwSaleDTO dto);
    void delete(DwSaleId id);
}
