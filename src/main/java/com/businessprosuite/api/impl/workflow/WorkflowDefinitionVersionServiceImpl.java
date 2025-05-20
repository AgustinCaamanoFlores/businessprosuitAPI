package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionVersionDTO;
import com.businessprosuite.api.model.workflow.WorkflowDefinitionVersion;
import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import com.businessprosuite.api.repository.workflow.WorkflowDefinitionVersionRepository;
import com.businessprosuite.api.repository.workflow.WorkflowDefinitionRepository;
import com.businessprosuite.api.service.workflow.WorkflowDefinitionVersionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowDefinitionVersionServiceImpl implements WorkflowDefinitionVersionService {

    private final WorkflowDefinitionVersionRepository versionRepo;
    private final WorkflowDefinitionRepository definitionRepo;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowDefinitionVersionDTO> findAll() {
        return versionRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowDefinitionVersionDTO findById(Integer id) {
        WorkflowDefinitionVersion ver = versionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkflowDefinitionVersion not found with id " + id));
        return toDto(ver);
    }

    @Override
    public WorkflowDefinitionVersionDTO create(WorkflowDefinitionVersionDTO dto) {
        WorkflowDefinition def = definitionRepo.findById(dto.getWorkflowDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + dto.getWorkflowDefinitionId()));

        WorkflowDefinitionVersion ver = new WorkflowDefinitionVersion();
        ver.setWf(def);
        ver.setSnapshotJson(dto.getSnapshotJson());
        ver.setEffectiveFrom(dto.getEffectiveFrom() != null ? dto.getEffectiveFrom() : java.time.LocalDateTime.now());
        WorkflowDefinitionVersion saved = versionRepo.save(ver);
        return toDto(saved);
    }

    @Override
    public WorkflowDefinitionVersionDTO update(Integer id, WorkflowDefinitionVersionDTO dto) {
        WorkflowDefinitionVersion ver = versionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinitionVersion not found with id " + id));
        WorkflowDefinition def = definitionRepo.findById(dto.getWorkflowDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + dto.getWorkflowDefinitionId()));
        ver.setWf(def);
        ver.setSnapshotJson(dto.getSnapshotJson());
        // effectiveFrom unchanged
        WorkflowDefinitionVersion updated = versionRepo.save(ver);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!versionRepo.existsById(id)) {
            throw new EntityNotFoundException("WorkflowDefinitionVersion not found with id " + id);
        }
        versionRepo.deleteById(id);
    }

    private WorkflowDefinitionVersionDTO toDto(WorkflowDefinitionVersion ver) {
        return WorkflowDefinitionVersionDTO.builder()
                .id(ver.getId())
                .workflowDefinitionId(ver.getWf().getId())
                .snapshotJson(ver.getSnapshotJson())
                .effectiveFrom(ver.getEffectiveFrom())
                .build();
    }
}
