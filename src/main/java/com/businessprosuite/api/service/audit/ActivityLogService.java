package com.businessprosuite.api.service.audit;

import com.businessprosuite.api.dto.audit.ActivityLogDTO;
import java.util.List;

public interface ActivityLogService {
    List<ActivityLogDTO> findAll();
    ActivityLogDTO findById(Integer id);
    ActivityLogDTO create(ActivityLogDTO dto);
    ActivityLogDTO update(Integer id, ActivityLogDTO dto);
    void delete(Integer id);
}