package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.ShiftDTO;
import java.util.List;

public interface ShiftService {
    List<ShiftDTO> findAll();
    ShiftDTO findById(Integer id);
    ShiftDTO create(ShiftDTO dto);
    ShiftDTO update(Integer id, ShiftDTO dto);
    void delete(Integer id);
}
