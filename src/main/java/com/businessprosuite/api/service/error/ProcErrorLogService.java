package com.businessprosuite.api.service.error;

import com.businessprosuite.api.dto.error.ProcErrorLogDTO;
import java.util.List;

public interface ProcErrorLogService {
    List<ProcErrorLogDTO> findAll();
    ProcErrorLogDTO findById(Integer id);
    ProcErrorLogDTO create(ProcErrorLogDTO dto);
    ProcErrorLogDTO update(Integer id, ProcErrorLogDTO dto);
    void delete(Integer id);
}