package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityUserDTO;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.security.SecurityUserService;
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
public class SecurityUserServiceImpl implements SecurityUserService {
    private final SecurityUserRepository userRepo;
    private final SecurityRoleRepository roleRepo;
    private final CompanyRepository companyRepo;

    @Override
    public List<SecurityUserDTO> findAll() {
        return userRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityUserDTO findById(Integer id) {
        SecurityUser u = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        return toDto(u);
    }

    @Override
    public SecurityUserDTO create(SecurityUserDTO dto) {
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        SecurityUser u = new SecurityUser();
        u.setSecusName(dto.getName());
        u.setSecusPassword(dto.getPassword());
        u.setSecusEmail(dto.getEmail());
        u.setSecusAvailable(dto.getAvailable());
        u.setSecusLastLogin(dto.getLastLogin());
        u.setSecusActive(dto.getActive());
        u.setSecusMfaEnabled(dto.getMfaEnabled());
        u.setSecusMfaSecret(dto.getMfaSecret());
        u.setSecusRole(role);
        u.setSecusCmp(cmp);
        u.setSecusLastPasswordChange(dto.getLastPasswordChange());
        u.setSecusFailedAttempts(dto.getFailedAttempts());
        u.setSecusResidence(dto.getResidence());
        u.setSecusCreatedAt(LocalDateTime.now());
        u.setSecusUpdatedAt(LocalDateTime.now());

        SecurityUser saved = userRepo.save(u);
        return toDto(saved);
    }

    @Override
    public SecurityUserDTO update(Integer id, SecurityUserDTO dto) {
        SecurityUser u = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        SecurityRole role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getRoleId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));

        u.setSecusName(dto.getName());
        u.setSecusPassword(dto.getPassword());
        u.setSecusEmail(dto.getEmail());
        u.setSecusAvailable(dto.getAvailable());
        u.setSecusLastLogin(dto.getLastLogin());
        u.setSecusActive(dto.getActive());
        u.setSecusMfaEnabled(dto.getMfaEnabled());
        u.setSecusMfaSecret(dto.getMfaSecret());
        u.setSecusRole(role);
        u.setSecusCmp(cmp);
        u.setSecusLastPasswordChange(dto.getLastPasswordChange());
        u.setSecusFailedAttempts(dto.getFailedAttempts());
        u.setSecusResidence(dto.getResidence());
        u.setSecusUpdatedAt(LocalDateTime.now());

        SecurityUser updated = userRepo.save(u);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("SecurityUser not found with id " + id);
        }
        userRepo.deleteById(id);
    }

    private SecurityUserDTO toDto(SecurityUser u) {
        return SecurityUserDTO.builder()
                .id(u.getId())
                .name(u.getSecusName())
                .password(u.getSecusPassword())
                .email(u.getSecusEmail())
                .available(u.getSecusAvailable())
                .lastLogin(u.getSecusLastLogin())
                .active(u.getSecusActive())
                .mfaEnabled(u.getSecusMfaEnabled())
                .mfaSecret(u.getSecusMfaSecret())
                .roleId(u.getSecusRole().getId())
                .companyId(u.getSecusCmp().getId())
                .lastPasswordChange(u.getSecusLastPasswordChange())
                .failedAttempts(u.getSecusFailedAttempts())
                .residence(u.getSecusResidence())
                .createdAt(u.getSecusCreatedAt())
                .updatedAt(u.getSecusUpdatedAt())
                .build();
    }
}
