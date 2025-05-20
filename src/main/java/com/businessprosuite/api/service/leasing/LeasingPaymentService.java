package com.businessprosuite.api.service.leasing;

import com.businessprosuite.api.dto.leasing.LeasingPaymentDTO;
import java.util.List;

public interface LeasingPaymentService {
    List<LeasingPaymentDTO> findAll();
    LeasingPaymentDTO findById(Integer id);
    LeasingPaymentDTO create(LeasingPaymentDTO dto);
    LeasingPaymentDTO update(Integer id, LeasingPaymentDTO dto);
    void delete(Integer id);
}