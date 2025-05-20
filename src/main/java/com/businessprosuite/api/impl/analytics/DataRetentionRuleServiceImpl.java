package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.DataRetentionRuleDTO;
import com.businessprosuite.api.model.analytics.DataRetentionRule;
import com.businessprosuite.api.repository.analytics.DataRetentionRuleRepository;
import com.businessprosuite.api.service.analytics.DataRetentionRuleService;
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
public class DataRetentionRuleServiceImpl implements DataRetentionRuleService {

    private final DataRetentionRuleRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<DataRetentionRuleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DataRetentionRuleDTO findById(Integer id) {
        DataRetentionRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DataRetentionRule not found with id " + id));
        return toDto(rule);
    }

    @Override
    public DataRetentionRuleDTO create(DataRetentionRuleDTO dto) {
        DataRetentionRule rule = new DataRetentionRule();
        rule.setTableName(dto.getTableName());
        rule.setKeepMonths(dto.getKeepMonths());
        rule.setLastRun(dto.getLastRun() != null ? dto.getLastRun() : LocalDateTime.now());
        DataRetentionRule saved = repo.save(rule);
        return toDto(saved);
    }

    @Override
    public DataRetentionRuleDTO update(Integer id, DataRetentionRuleDTO dto) {
        DataRetentionRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DataRetentionRule not found with id " + id));
        rule.setTableName(dto.getTableName());
        rule.setKeepMonths(dto.getKeepMonths());
        if (dto.getLastRun() != null) {
            rule.setLastRun(dto.getLastRun());
        }
        DataRetentionRule updated = repo.save(rule);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("DataRetentionRule not found with id " + id);
        }
        repo.deleteById(id);
    }

    private DataRetentionRuleDTO toDto(DataRetentionRule rule) {
        return DataRetentionRuleDTO.builder()
                .id(rule.getId())
                .tableName(rule.getTableName())
                .keepMonths(rule.getKeepMonths())
                .lastRun(rule.getLastRun())
                .build();
    }
}
