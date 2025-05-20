package com.businessprosuite.api.service.leasing;

import com.businessprosuite.api.dto.leasing.LeasingContractDTO;
import java.util.List;

public interface LeasingContractService {
    List<LeasingContractDTO> findAll();
    LeasingContractDTO findById(Integer id);
    LeasingContractDTO create(LeasingContractDTO dto);
    LeasingContractDTO update(Integer id, LeasingContractDTO dto);
    void delete(Integer id);
}