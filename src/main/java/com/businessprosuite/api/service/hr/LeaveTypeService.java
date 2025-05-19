package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.LeaveTypeDTO;
import java.util.List;

public interface LeaveTypeService {
    List<LeaveTypeDTO> findAll();
    LeaveTypeDTO findById(Integer id);
    LeaveTypeDTO create(LeaveTypeDTO dto);
    LeaveTypeDTO update(Integer id, LeaveTypeDTO dto);
    void delete(Integer id);
}
