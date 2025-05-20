package com.businessprosuite.api.service.etl;

import com.businessprosuite.api.dto.etl.RefreshLogDTO;
import java.util.List;

public interface RefreshLogService {
    List<RefreshLogDTO> findAll();
    RefreshLogDTO findById(Integer id);
    RefreshLogDTO create(RefreshLogDTO dto);
    RefreshLogDTO update(Integer id, RefreshLogDTO dto);
    void delete(Integer id);
}
