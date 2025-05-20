package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityRoleDTO;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.service.security.SecurityRoleService;
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
public class SecurityRoleServiceImpl implements SecurityRoleService {

    private final SecurityRoleRepository roleRepo;

    @Override
    public List<SecurityRoleDTO> findAll() {
        return roleRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityRoleDTO findById(Integer id) {
        SecurityRole role = roleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + id));
        return toDto(role);
    }

    @Override
    public SecurityRoleDTO create(SecurityRoleDTO dto) {
        SecurityRole role = new SecurityRole();
        role.setSecrlName(dto.getName());
        role.setSecrlDescription(dto.getDescription());
        if (dto.getParentRoleId() != null) {
            SecurityRole parent = roleRepo.findById(dto.getParentRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent SecurityRole not found with id " + dto.getParentRoleId()));
            role.setSecrlParentRole(parent);
        }
        role.setSecrlCreatedAt(LocalDateTime.now());
        role.setSecrlUpdatedAt(LocalDateTime.now());
        SecurityRole saved = roleRepo.save(role);
        return toDto(saved);
    }

    @Override
    public SecurityRoleDTO update(Integer id, SecurityRoleDTO dto) {
        SecurityRole role = roleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + id));
        role.setSecrlName(dto.getName());
        role.setSecrlDescription(dto.getDescription());
        if (dto.getParentRoleId() != null) {
            SecurityRole parent = roleRepo.findById(dto.getParentRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent SecurityRole not found with id " + dto.getParentRoleId()));
            role.setSecrlParentRole(parent);
        } else {
            role.setSecrlParentRole(null);
        }
        role.setSecrlUpdatedAt(LocalDateTime.now());
        SecurityRole updated = roleRepo.save(role);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!roleRepo.existsById(id)) {
            throw new EntityNotFoundException("SecurityRole not found with id " + id);
        }
        roleRepo.deleteById(id);
    }

    private SecurityRoleDTO toDto(SecurityRole role) {
        return SecurityRoleDTO.builder()
                .id(role.getId())
                .name(role.getSecrlName())
                .description(role.getSecrlDescription())
                .parentRoleId(role.getSecrlParentRole() != null ? role.getSecrlParentRole().getId() : null)
                .createdAt(role.getSecrlCreatedAt())
                .updatedAt(role.getSecrlUpdatedAt())
                .build();
    }
}