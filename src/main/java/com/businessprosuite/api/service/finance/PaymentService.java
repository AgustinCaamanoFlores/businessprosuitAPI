package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.PaymentDTO;
import java.util.List;

public interface PaymentService {
    List<PaymentDTO> findAll();
    PaymentDTO findById(Integer id);
    PaymentDTO create(PaymentDTO dto);
    PaymentDTO update(Integer id, PaymentDTO dto);
    void delete(Integer id);
}