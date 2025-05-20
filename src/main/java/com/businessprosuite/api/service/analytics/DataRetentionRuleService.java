package com.businessprosuite.api.service.analytics;

import com.businessprosuite.api.dto.analytics.DataRetentionRuleDTO;
import java.util.List;

public interface DataRetentionRuleService {
    List<DataRetentionRuleDTO> findAll();
    DataRetentionRuleDTO findById(Integer id);
    DataRetentionRuleDTO create(DataRetentionRuleDTO dto);
    DataRetentionRuleDTO update(Integer id, DataRetentionRuleDTO dto);
    void delete(Integer id);
}