package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.PeriodDTO;
import java.util.List;

public interface PeriodService {
    List<PeriodDTO> findAll();
    PeriodDTO findById(Integer id);
    PeriodDTO create(PeriodDTO dto);
    PeriodDTO update(Integer id, PeriodDTO dto);
    void delete(Integer id);
}