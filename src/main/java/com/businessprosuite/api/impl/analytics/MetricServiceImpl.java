package com.businessprosuite.api.impl.analytics;

import com.businessprosuite.api.dto.analytics.MetricDTO;
import com.businessprosuite.api.model.analytics.Metric;
import com.businessprosuite.api.repository.analytics.MetricRepository;
import com.businessprosuite.api.service.analytics.MetricService;
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
public class MetricServiceImpl implements MetricService {

    private final MetricRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<MetricDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MetricDTO findById(Integer id) {
        Metric metric = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metric not found with id " + id));
        return toDto(metric);
    }

    @Override
    public MetricDTO create(MetricDTO dto) {
        Metric metric = new Metric();
        metric.setName(dto.getName());
        metric.setValueNum(dto.getValueNum());
        metric.setRecordedAt(dto.getRecordedAt() != null ? dto.getRecordedAt() : LocalDateTime.now());
        Metric saved = repo.save(metric);
        return toDto(saved);
    }

    @Override
    public MetricDTO update(Integer id, MetricDTO dto) {
        Metric metric = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metric not found with id " + id));
        metric.setName(dto.getName());
        metric.setValueNum(dto.getValueNum());
        if (dto.getRecordedAt() != null) {
            metric.setRecordedAt(dto.getRecordedAt());
        }
        Metric updated = repo.save(metric);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Metric not found with id " + id);
        }
        repo.deleteById(id);
    }

    private MetricDTO toDto(Metric metric) {
        return MetricDTO.builder()
                .id(metric.getId())
                .name(metric.getName())
                .valueNum(metric.getValueNum())
                .recordedAt(metric.getRecordedAt())
                .build();
    }
}
