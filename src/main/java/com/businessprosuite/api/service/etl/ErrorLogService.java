package com.businessprosuite.api.service.etl;

import com.businessprosuite.api.dto.etl.ErrorLogDTO;
import java.util.List;

public interface ErrorLogService {
    List<ErrorLogDTO> findAll();
    ErrorLogDTO findById(Integer id);
    ErrorLogDTO create(ErrorLogDTO dto);
    ErrorLogDTO update(Integer id, ErrorLogDTO dto);
    void delete(Integer id);
}
