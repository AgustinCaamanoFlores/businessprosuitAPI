package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.WorkerPerformanceDTO;
import java.util.List;

public interface WorkerPerformanceService {
    List<WorkerPerformanceDTO> findAll();
    WorkerPerformanceDTO findById(Integer id);
    WorkerPerformanceDTO create(WorkerPerformanceDTO dto);
    WorkerPerformanceDTO update(Integer id, WorkerPerformanceDTO dto);
    void delete(Integer id);
}
