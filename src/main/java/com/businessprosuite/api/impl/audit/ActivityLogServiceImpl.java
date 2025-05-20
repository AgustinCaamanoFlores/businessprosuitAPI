package com.businessprosuite.api.impl.audit;

import com.businessprosuite.api.dto.audit.ActivityLogDTO;
import com.businessprosuite.api.model.audit.ActivityLog;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.audit.ActivityLogRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.audit.ActivityLogService;
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
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository logRepo;
    private final SecurityUserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLogDTO> findAll() {
        return logRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityLogDTO findById(Integer id) {
        ActivityLog log = logRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ActivityLog not found with id " + id));
        return toDto(log);
    }

    @Override
    public ActivityLogDTO create(ActivityLogDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        ActivityLog log = new ActivityLog();
        log.setAlUser(user);
        log.setAlAction(dto.getAction());
        log.setAlDescription(dto.getDescription());
        log.setAlDate(dto.getDate() != null ? dto.getDate() : LocalDateTime.now());
        log.setAlCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        ActivityLog saved = logRepo.save(log);
        return toDto(saved);
    }

    @Override
    public ActivityLogDTO update(Integer id, ActivityLogDTO dto) {
        ActivityLog log = logRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ActivityLog not found with id " + id));
        if (dto.getUserId() != null) {
            SecurityUser user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
            log.setAlUser(user);
        }
        log.setAlAction(dto.getAction());
        log.setAlDescription(dto.getDescription());
        if (dto.getDate() != null) {
            log.setAlDate(dto.getDate());
        }
        // preserve createdAt
        ActivityLog updated = logRepo.save(log);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!logRepo.existsById(id)) {
            throw new EntityNotFoundException("ActivityLog not found with id " + id);
        }
        logRepo.deleteById(id);
    }

    private ActivityLogDTO toDto(ActivityLog log) {
        return ActivityLogDTO.builder()
                .id(log.getId())
                .userId(log.getAlUser().getId())
                .action(log.getAlAction())
                .description(log.getAlDescription())
                .date(log.getAlDate())
                .createdAt(log.getAlCreatedAt())
                .build();
    }
}
