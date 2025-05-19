package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.WorkerDTO;
import java.util.List;

public interface WorkerService {
    List<WorkerDTO> findAll();
    WorkerDTO findById(Integer id);
    WorkerDTO create(WorkerDTO dto);
    WorkerDTO update(Integer id, WorkerDTO dto);
    void delete(Integer id);
}
