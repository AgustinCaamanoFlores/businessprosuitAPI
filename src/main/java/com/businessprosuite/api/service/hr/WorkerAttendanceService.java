package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.WorkerAttendanceDTO;
import java.util.List;

public interface WorkerAttendanceService {
    List<WorkerAttendanceDTO> findAll();
    WorkerAttendanceDTO findById(Integer id);
    WorkerAttendanceDTO create(WorkerAttendanceDTO dto);
    WorkerAttendanceDTO update(Integer id, WorkerAttendanceDTO dto);
    void delete(Integer id);
}
