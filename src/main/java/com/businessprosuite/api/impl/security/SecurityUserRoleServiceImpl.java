package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityUserRoleDTO;
import com.businessprosuite.api.model.security.SecurityUserRole;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.repository.security.SecurityUserRoleRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.service.security.SecurityUserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityUserRoleServiceImpl implements SecurityUserRoleService {

    private final SecurityUserRoleRepository repo;
    private final SecurityUserRepository userRepo;
    private final SecurityRoleRepository roleRepo;

    @Override
    public List<SecurityUserRoleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityUserRoleDTO findById(Integer id) {
        SecurityUserRole ur = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUserRole not found with id " + id));
        return toDto(ur);
    }

    @Override
    public SecurityUserRoleDTO create(SecurityUserRoleDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        SecurityRole role = roleRepo.findById(dto.getRoleId()).orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));

        SecurityUserRole ur = new SecurityUserRole();
        ur.setSecUrSecus(user);
        ur.setSecUrSecrl(role);
        SecurityUserRole saved = repo.save(ur);
        return toDto(saved);
    }

    @Override
    public SecurityUserRoleDTO update(Integer id, SecurityUserRoleDTO dto) {
        SecurityUserRole ur = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUserRole not found with id " + id));
        SecurityUser user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        SecurityRole role = roleRepo.findById(dto.getRoleId()).orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));

        ur.setSecUrSecus(user);
        ur.setSecUrSecrl(role);
        SecurityUserRole updated = repo.save(ur);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityUserRole not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityUserRoleDTO toDto(SecurityUserRole ur) {
        return SecurityUserRoleDTO.builder()
                .id(ur.getId())
                .userId(ur.getSecUrSecus().getId())
                .roleId(ur.getSecUrSecrl().getId())
                .build();
    }
}