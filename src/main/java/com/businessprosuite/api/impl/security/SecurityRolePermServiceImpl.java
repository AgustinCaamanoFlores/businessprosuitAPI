package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityRolePermDTO;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.model.security.SecurityPermission;
import com.businessprosuite.api.model.security.SecurityRolePerm;
import com.businessprosuite.api.repository.security.SecurityRolePermRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.repository.security.SecurityPermissionRepository;
import com.businessprosuite.api.service.security.SecurityRolePermService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityRolePermServiceImpl implements SecurityRolePermService {

    private final SecurityRolePermRepository repo;
    private final SecurityRoleRepository roleRepo;
    private final SecurityPermissionRepository permRepo;

    @Override
    public List<SecurityRolePermDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityRolePermDTO findById(Integer id) {
        SecurityRolePerm rp = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityRolePerm not found with id " + id));
        return toDto(rp);
    }

    @Override
    public SecurityRolePermDTO create(SecurityRolePermDTO dto) {
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        SecurityPermission perm = permRepo.findById(dto.getPermissionId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityPermission not found with id " + dto.getPermissionId()));

        SecurityRolePerm rp = new SecurityRolePerm();
        rp.setSecRpSecrl(role);
        rp.setSecRpSecperm(perm);
        SecurityRolePerm saved = repo.save(rp);
        return toDto(saved);
    }

    @Override
    public SecurityRolePermDTO update(Integer id, SecurityRolePermDTO dto) {
        SecurityRolePerm rp = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityRolePerm not found with id " + id));
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        SecurityPermission perm = permRepo.findById(dto.getPermissionId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityPermission not found with id " + dto.getPermissionId()));

        rp.setSecRpSecrl(role);
        rp.setSecRpSecperm(perm);
        SecurityRolePerm updated = repo.save(rp);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityRolePerm not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityRolePermDTO toDto(SecurityRolePerm rp) {
        return SecurityRolePermDTO.builder()
                .id(rp.getId())
                .roleId(rp.getSecRpSecrl().getId())
                .permissionId(rp.getSecRpSecperm().getId())
                .build();
    }
}