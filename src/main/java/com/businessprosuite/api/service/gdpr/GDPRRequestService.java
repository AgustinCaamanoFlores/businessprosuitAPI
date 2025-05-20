package com.businessprosuite.api.service.gdpr;

import com.businessprosuite.api.dto.gdpr.GDPRRequestDTO;
import java.util.List;

public interface GDPRRequestService {
    List<GDPRRequestDTO> findAll();
    GDPRRequestDTO findById(Integer id);
    GDPRRequestDTO create(GDPRRequestDTO dto);
    GDPRRequestDTO update(Integer id, GDPRRequestDTO dto);
    void delete(Integer id);
}
