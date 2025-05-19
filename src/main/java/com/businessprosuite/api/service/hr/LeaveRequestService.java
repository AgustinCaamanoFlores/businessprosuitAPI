package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.LeaveRequestDTO;
import java.util.List;

public interface LeaveRequestService {
    List<LeaveRequestDTO> findAll();
    LeaveRequestDTO findById(Integer id);
    LeaveRequestDTO create(LeaveRequestDTO dto);
    LeaveRequestDTO update(Integer id, LeaveRequestDTO dto);
    void delete(Integer id);
}
