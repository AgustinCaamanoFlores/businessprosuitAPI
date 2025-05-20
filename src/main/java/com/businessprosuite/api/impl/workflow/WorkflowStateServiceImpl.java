package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowStateDTO;
import com.businessprosuite.api.model.workflow.WorkflowState;
import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import com.businessprosuite.api.repository.workflow.WorkflowStateRepository;
import com.businessprosuite.api.repository.workflow.WorkflowDefinitionRepository;
import com.businessprosuite.api.service.workflow.WorkflowStateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowStateServiceImpl implements WorkflowStateService {

    private final WorkflowStateRepository stateRepo;
    private final WorkflowDefinitionRepository defRepo;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowStateDTO> findAll() {
        return stateRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowStateDTO findById(Integer id) {
        WorkflowState state = stateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowState not found with id " + id));
        return toDto(state);
    }

    @Override
    public WorkflowStateDTO create(WorkflowStateDTO dto) {
        WorkflowDefinition def = defRepo.findById(dto.getDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + dto.getDefinitionId()));
        WorkflowState state = new WorkflowState();
        state.setWf(def);
        state.setWfEstNombre(dto.getName());
        state.setWfEstOrden(dto.getOrder());
        WorkflowState saved = stateRepo.save(state);
        return toDto(saved);
    }

    @Override
    public WorkflowStateDTO update(Integer id, WorkflowStateDTO dto) {
        WorkflowState state = stateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowState not found with id " + id));
        WorkflowDefinition def = defRepo.findById(dto.getDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + dto.getDefinitionId()));
        state.setWf(def);
        state.setWfEstNombre(dto.getName());
        state.setWfEstOrden(dto.getOrder());
        WorkflowState updated = stateRepo.save(state);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!stateRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    "WorkflowState not found with id " + id);
        }
        stateRepo.deleteById(id);
    }

    private WorkflowStateDTO toDto(WorkflowState state) {
        return WorkflowStateDTO.builder()
                .id(state.getId())
                .definitionId(state.getWf().getId())
                .name(state.getWfEstNombre())
                .order(state.getWfEstOrden())
                .build();
    }
}
