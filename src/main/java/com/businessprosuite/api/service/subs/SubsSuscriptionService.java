package com.businessprosuite.api.service.subs;

import com.businessprosuite.api.dto.subs.SubsSuscriptionDTO;
import java.util.List;

public interface SubsSuscriptionService {
    List<SubsSuscriptionDTO> findAll();
    SubsSuscriptionDTO findById(Integer id);
    SubsSuscriptionDTO create(SubsSuscriptionDTO dto);
    SubsSuscriptionDTO update(Integer id, SubsSuscriptionDTO dto);
    void delete(Integer id);
}