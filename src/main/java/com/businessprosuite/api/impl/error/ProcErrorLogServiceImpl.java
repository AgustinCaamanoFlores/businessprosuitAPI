package com.businessprosuite.api.impl.error;

import com.businessprosuite.api.dto.error.ProcErrorLogDTO;
import com.businessprosuite.api.model.error.ProcErrorLog;
import com.businessprosuite.api.repository.error.ProcErrorLogRepository;
import com.businessprosuite.api.service.error.ProcErrorLogService;
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
public class ProcErrorLogServiceImpl implements ProcErrorLogService {

    private final ProcErrorLogRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<ProcErrorLogDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProcErrorLogDTO findById(Integer id) {
        ProcErrorLog log = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProcErrorLog not found with id " + id));
        return toDto(log);
    }

    @Override
    public ProcErrorLogDTO create(ProcErrorLogDTO dto) {
        ProcErrorLog log = new ProcErrorLog();
        log.setProcedureName(dto.getProcedureName());
        log.setErrorMessage(dto.getErrorMessage());
        log.setErrorTimestamp(dto.getErrorTimestamp() != null ? dto.getErrorTimestamp() : LocalDateTime.now());
        ProcErrorLog saved = repo.save(log);
        return toDto(saved);
    }

    @Override
    public ProcErrorLogDTO update(Integer id, ProcErrorLogDTO dto) {
        ProcErrorLog log = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProcErrorLog not found with id " + id));
        log.setProcedureName(dto.getProcedureName());
        log.setErrorMessage(dto.getErrorMessage());
        // preserve original timestamp unless provided
        if (dto.getErrorTimestamp() != null) {
            log.setErrorTimestamp(dto.getErrorTimestamp());
        }
        ProcErrorLog updated = repo.save(log);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(
                    "ProcErrorLog not found with id " + id);
        }
        repo.deleteById(id);
    }

    private ProcErrorLogDTO toDto(ProcErrorLog log) {
        return ProcErrorLogDTO.builder()
                .id(log.getId())
                .procedureName(log.getProcedureName())
                .errorMessage(log.getErrorMessage())
                .errorTimestamp(log.getErrorTimestamp())
                .build();
    }
}