package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityPermissionDTO;
import com.businessprosuite.api.model.security.SecurityPermission;
import com.businessprosuite.api.repository.security.SecurityPermissionRepository;
import com.businessprosuite.api.service.security.SecurityPermissionService;
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
public class SecurityPermissionServiceImpl implements SecurityPermissionService {

    private final SecurityPermissionRepository repo;

    @Override
    public List<SecurityPermissionDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityPermissionDTO findById(Integer id) {
        SecurityPermission perm = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPermission not found with id " + id));
        return toDto(perm);
    }

    @Override
    public SecurityPermissionDTO create(SecurityPermissionDTO dto) {
        SecurityPermission perm = new SecurityPermission();
        perm.setSecpermName(dto.getName());
        perm.setSecpermCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        perm.setSecpermUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        SecurityPermission saved = repo.save(perm);
        return toDto(saved);
    }

    @Override
    public SecurityPermissionDTO update(Integer id, SecurityPermissionDTO dto) {
        SecurityPermission perm = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPermission not found with id " + id));
        perm.setSecpermName(dto.getName());
        perm.setSecpermUpdatedAt(LocalDateTime.now());
        SecurityPermission updated = repo.save(perm);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityPermission not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityPermissionDTO toDto(SecurityPermission perm) {
        return SecurityPermissionDTO.builder()
                .id(perm.getId())
                .name(perm.getSecpermName())
                .createdAt(perm.getSecpermCreatedAt())
                .updatedAt(perm.getSecpermUpdatedAt())
                .build();
    }
}
