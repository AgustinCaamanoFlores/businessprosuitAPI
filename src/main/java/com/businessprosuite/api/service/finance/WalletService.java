package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.WalletDTO;
import java.util.List;

public interface WalletService {
    List<WalletDTO> findAll();
    WalletDTO findById(Integer id);
    WalletDTO create(WalletDTO dto);
    WalletDTO update(Integer id, WalletDTO dto);
    void delete(Integer id);
}