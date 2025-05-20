package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityPasswordPolicyDTO;
import com.businessprosuite.api.model.security.SecurityPasswordPolicy;
import com.businessprosuite.api.repository.security.SecurityPasswordPolicyRepository;
import com.businessprosuite.api.service.security.SecurityPasswordPolicyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityPasswordPolicyServiceImpl implements SecurityPasswordPolicyService {

    private final SecurityPasswordPolicyRepository repo;

    @Override
    public List<SecurityPasswordPolicyDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityPasswordPolicyDTO findById(Integer id) {
        SecurityPasswordPolicy policy = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPasswordPolicy not found with id " + id));
        return toDto(policy);
    }

    @Override
    public SecurityPasswordPolicyDTO create(SecurityPasswordPolicyDTO dto) {
        SecurityPasswordPolicy policy = new SecurityPasswordPolicy();
        policy.setDescription(dto.getDescription());
        policy.setMinLength(dto.getMinLength());
        policy.setRequireUpper(dto.getRequireUpper());
        policy.setRequireDigit(dto.getRequireDigit());
        policy.setExpireDays(dto.getExpireDays());
        policy.setReuseForbid(dto.getReuseForbid());
        SecurityPasswordPolicy saved = repo.save(policy);
        return toDto(saved);
    }

    @Override
    public SecurityPasswordPolicyDTO update(Integer id, SecurityPasswordPolicyDTO dto) {
        SecurityPasswordPolicy policy = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPasswordPolicy not found with id " + id));
        policy.setDescription(dto.getDescription());
        policy.setMinLength(dto.getMinLength());
        policy.setRequireUpper(dto.getRequireUpper());
        policy.setRequireDigit(dto.getRequireDigit());
        policy.setExpireDays(dto.getExpireDays());
        policy.setReuseForbid(dto.getReuseForbid());
        SecurityPasswordPolicy updated = repo.save(policy);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityPasswordPolicy not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityPasswordPolicyDTO toDto(SecurityPasswordPolicy policy) {
        return SecurityPasswordPolicyDTO.builder()
                .id(policy.getId())
                .description(policy.getDescription())
                .minLength(policy.getMinLength())
                .requireUpper(policy.getRequireUpper())
                .requireDigit(policy.getRequireDigit())
                .expireDays(policy.getExpireDays())
                .reuseForbid(policy.getReuseForbid())
                .build();
    }
}