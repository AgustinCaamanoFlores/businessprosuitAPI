package com.businessprosuite.api.impl.etl;

import com.businessprosuite.api.dto.etl.RefreshLogDTO;
import com.businessprosuite.api.model.etl.RefreshLog;
import com.businessprosuite.api.repository.etl.RefreshLogRepository;
import com.businessprosuite.api.service.etl.RefreshLogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshLogServiceImpl implements RefreshLogService {

    private final RefreshLogRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<RefreshLogDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshLogDTO findById(Integer id) {
        RefreshLog log = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RefreshLog not found with id " + id));
        return toDto(log);
    }

    @Override
    public RefreshLogDTO create(RefreshLogDTO dto) {
        RefreshLog log = new RefreshLog();
        log.setProcessName(dto.getProcessName());
        log.setStartedAt(dto.getStartedAt());
        log.setFinishedAt(dto.getFinishedAt());
        log.setRowsProcessed(dto.getRowsProcessed());
        log.setStatus(dto.getStatus());
        log.setMessage(dto.getMessage());
        RefreshLog saved = repository.save(log);
        return toDto(saved);
    }

    @Override
    public RefreshLogDTO update(Integer id, RefreshLogDTO dto) {
        RefreshLog log = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RefreshLog not found with id " + id));
        log.setProcessName(dto.getProcessName());
        log.setStartedAt(dto.getStartedAt());
        log.setFinishedAt(dto.getFinishedAt());
        log.setRowsProcessed(dto.getRowsProcessed());
        log.setStatus(dto.getStatus());
        log.setMessage(dto.getMessage());
        RefreshLog updated = repository.save(log);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("RefreshLog not found with id " + id);
        }
        repository.deleteById(id);
    }

    private RefreshLogDTO toDto(RefreshLog log) {
        return RefreshLogDTO.builder()
                .id(log.getId())
                .processName(log.getProcessName())
                .startedAt(log.getStartedAt())
                .finishedAt(log.getFinishedAt())
                .rowsProcessed(log.getRowsProcessed())
                .status(log.getStatus())
                .message(log.getMessage())
                .build();
    }
}
