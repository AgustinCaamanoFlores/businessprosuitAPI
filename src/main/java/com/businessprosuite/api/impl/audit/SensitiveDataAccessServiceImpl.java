package com.businessprosuite.api.impl.audit;

import com.businessprosuite.api.dto.audit.SensitiveDataAccessDTO;
import com.businessprosuite.api.model.audit.SensitiveDataAccess;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.audit.SensitiveDataAccessRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.audit.SensitiveDataAccessService;
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
public class SensitiveDataAccessServiceImpl implements SensitiveDataAccessService {

    private final SensitiveDataAccessRepository repo;
    private final SecurityUserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<SensitiveDataAccessDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SensitiveDataAccessDTO findById(Integer id) {
        SensitiveDataAccess sda = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SensitiveDataAccess not found with id " + id));
        return toDto(sda);
    }

    @Override
    public SensitiveDataAccessDTO create(SensitiveDataAccessDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        SensitiveDataAccess sda = new SensitiveDataAccess();
        sda.setSdaUser(user);
        sda.setSdaAccessedAt(dto.getAccessedAt() != null ? dto.getAccessedAt() : LocalDateTime.now());
        sda.setSdaAccessedField(dto.getAccessedField());
        sda.setSdaAccessReason(dto.getAccessReason());
        SensitiveDataAccess saved = repo.save(sda);
        return toDto(saved);
    }

    @Override
    public SensitiveDataAccessDTO update(Integer id, SensitiveDataAccessDTO dto) {
        SensitiveDataAccess sda = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SensitiveDataAccess not found with id " + id));
        if (dto.getUserId() != null) {
            SecurityUser user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
            sda.setSdaUser(user);
        }
        if (dto.getAccessedAt() != null) {
            sda.setSdaAccessedAt(dto.getAccessedAt());
        }
        sda.setSdaAccessedField(dto.getAccessedField());
        sda.setSdaAccessReason(dto.getAccessReason());
        SensitiveDataAccess updated = repo.save(sda);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SensitiveDataAccess not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SensitiveDataAccessDTO toDto(SensitiveDataAccess sda) {
        return SensitiveDataAccessDTO.builder()
                .id(sda.getId())
                .userId(sda.getSdaUser().getId())
                .accessedAt(sda.getSdaAccessedAt())
                .accessedField(sda.getSdaAccessedField())
                .accessReason(sda.getSdaAccessReason())
                .build();
    }
}
