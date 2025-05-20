package com.businessprosuite.api.service.core;

import com.businessprosuite.api.dto.core.TestResultDTO;
import java.util.List;

public interface TestResultService {
    List<TestResultDTO> findAll();
    TestResultDTO findById(Integer id);
    TestResultDTO create(TestResultDTO dto);
    TestResultDTO update(Integer id, TestResultDTO dto);
    void delete(Integer id);
}