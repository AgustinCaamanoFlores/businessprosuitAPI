package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityPasswordHistoryDTO;
import com.businessprosuite.api.model.security.SecurityPasswordHistory;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityPasswordHistoryRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.security.SecurityPasswordHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityPasswordHistoryServiceImpl implements SecurityPasswordHistoryService {

    private final SecurityPasswordHistoryRepository historyRepo;
    private final SecurityUserRepository userRepo;

    @Override
    public List<SecurityPasswordHistoryDTO> findAll() {
        return historyRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityPasswordHistoryDTO findById(Integer id) {
        SecurityPasswordHistory ph = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPasswordHistory not found with id " + id));
        return toDto(ph);
    }

    @Override
    public SecurityPasswordHistoryDTO create(SecurityPasswordHistoryDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        SecurityPasswordHistory ph = new SecurityPasswordHistory();
        ph.setSecphSecus(user);
        ph.setSecphPasswordHash(dto.getPasswordHash());
        ph.setSecphChangedAt(dto.getChangedAt() != null ? dto.getChangedAt() : OffsetDateTime.now());

        SecurityPasswordHistory saved = historyRepo.save(ph);
        return toDto(saved);
    }

    @Override
    public SecurityPasswordHistoryDTO update(Integer id, SecurityPasswordHistoryDTO dto) {
        SecurityPasswordHistory ph = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPasswordHistory not found with id " + id));
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        ph.setSecphSecus(user);
        ph.setSecphPasswordHash(dto.getPasswordHash());
        ph.setSecphChangedAt(dto.getChangedAt());

        SecurityPasswordHistory updated = historyRepo.save(ph);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!historyRepo.existsById(id)) {
            throw new EntityNotFoundException("SecurityPasswordHistory not found with id " + id);
        }
        historyRepo.deleteById(id);
    }

    private SecurityPasswordHistoryDTO toDto(SecurityPasswordHistory ph) {
        return SecurityPasswordHistoryDTO.builder()
                .id(ph.getId())
                .userId(ph.getSecphSecus().getId())
                .passwordHash(ph.getSecphPasswordHash())
                .changedAt(ph.getSecphChangedAt())
                .build();
    }
}
