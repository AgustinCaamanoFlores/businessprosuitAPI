package com.businessprosuite.api.impl.core;

import com.businessprosuite.api.dto.core.TestResultDTO;
import com.businessprosuite.api.model.core.TestResult;
import com.businessprosuite.api.repository.core.TestResultRepository;
import com.businessprosuite.api.service.core.TestResultService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepo;

    @Override
    @Transactional(readOnly = true)
    public List<TestResultDTO> findAll() {
        return testResultRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TestResultDTO findById(Integer id) {
        TestResult tr = testResultRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestResult not found with id " + id));
        return toDto(tr);
    }

    @Override
    public TestResultDTO create(TestResultDTO dto) {
        TestResult tr = new TestResult();
        tr.setTestName(dto.getTestName());
        tr.setTestResult(dto.getTestResult());
        tr.setTestMessage(dto.getTestMessage());
        tr.setTestDate(dto.getTestDate() != null ? dto.getTestDate() : LocalDateTime.now());
        TestResult saved = testResultRepo.save(tr);
        return toDto(saved);
    }

    @Override
    public TestResultDTO update(Integer id, TestResultDTO dto) {
        TestResult tr = testResultRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestResult not found with id " + id));
        tr.setTestName(dto.getTestName());
        tr.setTestResult(dto.getTestResult());
        tr.setTestMessage(dto.getTestMessage());
        if (dto.getTestDate() != null) {
            tr.setTestDate(dto.getTestDate());
        }
        TestResult updated = testResultRepo.save(tr);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!testResultRepo.existsById(id)) {
            throw new EntityNotFoundException("TestResult not found with id " + id);
        }
        testResultRepo.deleteById(id);
    }

    private TestResultDTO toDto(TestResult tr) {
        return TestResultDTO.builder()
                .id(tr.getId())
                .testName(tr.getTestName())
                .testResult(tr.getTestResult())
                .testMessage(tr.getTestMessage())
                .testDate(tr.getTestDate())
                .build();
    }
}