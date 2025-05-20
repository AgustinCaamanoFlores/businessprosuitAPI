package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.KpiDefDTO;
import java.util.List;

public interface KpiDefService {
    List<KpiDefDTO> findAll();
    KpiDefDTO findById(Integer id);
    KpiDefDTO create(KpiDefDTO dto);
    KpiDefDTO update(Integer id, KpiDefDTO dto);
    void delete(Integer id);
}