package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.WorkerDepartmentDTO;
import java.util.List;

public interface WorkerDepartmentService {
    List<WorkerDepartmentDTO> findAll();
    WorkerDepartmentDTO findById(Integer id);
    WorkerDepartmentDTO create(WorkerDepartmentDTO dto);
    WorkerDepartmentDTO update(Integer id, WorkerDepartmentDTO dto);
    void delete(Integer id);
}
