package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityPolicyRuleDTO;
import com.businessprosuite.api.model.security.SecurityPolicyRule;
import com.businessprosuite.api.repository.security.SecurityPolicyRuleRepository;
import com.businessprosuite.api.service.security.SecurityPolicyRuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityPolicyRuleServiceImpl implements SecurityPolicyRuleService {

    private final SecurityPolicyRuleRepository repo;

    @Override
    public List<SecurityPolicyRuleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityPolicyRuleDTO findById(Integer id) {
        SecurityPolicyRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPolicyRule not found with id " + id));
        return toDto(rule);
    }

    @Override
    public SecurityPolicyRuleDTO create(SecurityPolicyRuleDTO dto) {
        SecurityPolicyRule rule = new SecurityPolicyRule();
        rule.setExpr(dto.getExpr());
        rule.setDescription(dto.getDescription());
        SecurityPolicyRule saved = repo.save(rule);
        return toDto(saved);
    }

    @Override
    public SecurityPolicyRuleDTO update(Integer id, SecurityPolicyRuleDTO dto) {
        SecurityPolicyRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityPolicyRule not found with id " + id));
        rule.setExpr(dto.getExpr());
        rule.setDescription(dto.getDescription());
        SecurityPolicyRule updated = repo.save(rule);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityPolicyRule not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityPolicyRuleDTO toDto(SecurityPolicyRule rule) {
        return SecurityPolicyRuleDTO.builder()
                .id(rule.getId())
                .expr(rule.getExpr())
                .description(rule.getDescription())
                .build();
    }
}