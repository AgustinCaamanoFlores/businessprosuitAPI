package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.MatViewSaleDTO;
import com.businessprosuite.api.model.analytics.MatViewSaleId;
import java.util.List;

public interface MatViewSaleService {
    List<MatViewSaleDTO> findAll();
    MatViewSaleDTO findById(MatViewSaleId id);
    MatViewSaleDTO create(MatViewSaleDTO dto);
    MatViewSaleDTO update(MatViewSaleId id, MatViewSaleDTO dto);
    void delete(MatViewSaleId id);
}