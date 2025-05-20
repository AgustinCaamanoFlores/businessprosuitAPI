package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.DwTimeDTO;
import java.time.LocalDate;
import java.util.List;

public interface DwTimeService {
    List<DwTimeDTO> findAll();
    DwTimeDTO findByDate(LocalDate date);
    DwTimeDTO create(DwTimeDTO dto);
    DwTimeDTO update(LocalDate date, DwTimeDTO dto);
    void delete(LocalDate date);
}