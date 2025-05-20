package com.businessprosuite.api.impl.etl;

import com.businessprosuite.api.dto.etl.ErrorLogDTO;
import com.businessprosuite.api.model.etl.ErrorLog;
import com.businessprosuite.api.repository.etl.ErrorLogRepository;
import com.businessprosuite.api.service.etl.ErrorLogService;
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
public class ErrorLogServiceImpl implements ErrorLogService {

    private final ErrorLogRepository errorLogRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ErrorLogDTO> findAll() {
        return errorLogRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ErrorLogDTO findById(Integer id) {
        ErrorLog err = errorLogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ErrorLog not found with id " + id));
        return toDto(err);
    }

    @Override
    public ErrorLogDTO create(ErrorLogDTO dto) {
        ErrorLog err = new ErrorLog();
        err.setErrorMessage(dto.getErrorMessage());
        err.setErrorDate(dto.getErrorDate() != null ? dto.getErrorDate() : LocalDateTime.now());
        ErrorLog saved = errorLogRepo.save(err);
        return toDto(saved);
    }

    @Override
    public ErrorLogDTO update(Integer id, ErrorLogDTO dto) {
        ErrorLog err = errorLogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ErrorLog not found with id " + id));
        err.setErrorMessage(dto.getErrorMessage());
        // Do not update errorDate to preserve original timestamp
        ErrorLog updated = errorLogRepo.save(err);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!errorLogRepo.existsById(id)) {
            throw new EntityNotFoundException("ErrorLog not found with id " + id);
        }
        errorLogRepo.deleteById(id);
    }

    private ErrorLogDTO toDto(ErrorLog err) {
        return ErrorLogDTO.builder()
                .id(err.getId())
                .errorMessage(err.getErrorMessage())
                .errorDate(err.getErrorDate())
                .build();
    }
}