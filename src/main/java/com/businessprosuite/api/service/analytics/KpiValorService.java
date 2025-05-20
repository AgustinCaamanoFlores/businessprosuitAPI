package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.KpiValorDTO;
import java.util.List;

public interface KpiValorService {
    List<KpiValorDTO> findAll();
    KpiValorDTO findById(Integer id);
    KpiValorDTO create(KpiValorDTO dto);
    KpiValorDTO update(Integer id, KpiValorDTO dto);
    void delete(Integer id);
}
