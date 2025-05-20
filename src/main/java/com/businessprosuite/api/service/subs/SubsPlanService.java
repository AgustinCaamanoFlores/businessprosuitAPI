package com.businessprosuite.api.service.subs;

import com.businessprosuite.api.dto.subs.SubsPlanDTO;
import java.util.List;

public interface SubsPlanService {
    List<SubsPlanDTO> findAll();
    SubsPlanDTO findById(Integer id);
    SubsPlanDTO create(SubsPlanDTO dto);
    SubsPlanDTO update(Integer id, SubsPlanDTO dto);
    void delete(Integer id);
}