package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.MetricDTO;
import java.util.List;

public interface MetricService {
    List<MetricDTO> findAll();
    MetricDTO findById(Integer id);
    MetricDTO create(MetricDTO dto);
    MetricDTO update(Integer id, MetricDTO dto);
    void delete(Integer id);
}
