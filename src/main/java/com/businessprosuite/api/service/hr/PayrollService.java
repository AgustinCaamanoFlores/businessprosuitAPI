package com.businessprosuite.api.service.hr;

import com.businessprosuite.api.dto.hr.PayrollDTO;
import java.util.List;

public interface PayrollService {
    List<PayrollDTO> findAll();
    PayrollDTO findById(Integer id);
    PayrollDTO create(PayrollDTO dto);
    PayrollDTO update(Integer id, PayrollDTO dto);
    void delete(Integer id);
}
