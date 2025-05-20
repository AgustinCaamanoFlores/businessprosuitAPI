package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowAssignmentRuleDTO;
import com.businessprosuite.api.model.workflow.WorkflowAssignmentRule;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.repository.workflow.WorkflowAssignmentRuleRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.service.workflow.WorkflowAssignmentRuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowAssignmentRuleServiceImpl implements WorkflowAssignmentRuleService {

    private final WorkflowAssignmentRuleRepository repo;
    private final SecurityRoleRepository roleRepo;

    @Override
    public List<WorkflowAssignmentRuleDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkflowAssignmentRuleDTO findById(Integer id) {
        WorkflowAssignmentRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkflowAssignmentRule not found with id " + id));
        return toDto(rule);
    }

    @Override
    public WorkflowAssignmentRuleDTO create(WorkflowAssignmentRuleDTO dto) {
        SecurityRole role = roleRepo.findById(dto.getTargetRoleId()).orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getTargetRoleId()));
        WorkflowAssignmentRule rule = new WorkflowAssignmentRule();
        rule.setExpr(dto.getExpr());
        rule.setTargetRole(role);
        WorkflowAssignmentRule saved = repo.save(rule);
        return toDto(saved);
    }

    @Override
    public WorkflowAssignmentRuleDTO update(Integer id, WorkflowAssignmentRuleDTO dto) {
        WorkflowAssignmentRule rule = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkflowAssignmentRule not found with id " + id));
        SecurityRole role = roleRepo.findById(dto.getTargetRoleId()).orElseThrow(() -> new EntityNotFoundException("SecurityRole not found with id " + dto.getTargetRoleId()));
        rule.setExpr(dto.getExpr());
        rule.setTargetRole(role);
        WorkflowAssignmentRule updated = repo.save(rule);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("WorkflowAssignmentRule not found with id " + id);
        }
        repo.deleteById(id);
    }

    private WorkflowAssignmentRuleDTO toDto(WorkflowAssignmentRule rule) {
        return WorkflowAssignmentRuleDTO.builder()
                .id(rule.getId())
                .expr(rule.getExpr())
                .targetRoleId(rule.getTargetRole().getId())
                .build();
    }
}
