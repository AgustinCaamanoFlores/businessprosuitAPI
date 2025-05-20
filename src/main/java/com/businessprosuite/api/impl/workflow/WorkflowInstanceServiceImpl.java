package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowInstanceDTO;
import com.businessprosuite.api.model.workflow.WorkflowInstance;
import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import com.businessprosuite.api.model.workflow.WorkflowState;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.workflow.WorkflowInstanceRepository;
import com.businessprosuite.api.repository.workflow.WorkflowDefinitionRepository;
import com.businessprosuite.api.repository.workflow.WorkflowStateRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.workflow.WorkflowInstanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowInstanceServiceImpl implements WorkflowInstanceService {
    private final WorkflowInstanceRepository instanceRepo;
    private final WorkflowDefinitionRepository definitionRepo;
    private final WorkflowStateRepository stateRepo;
    private final SecurityUserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowInstanceDTO> findAll() {
        return instanceRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowInstanceDTO findById(Integer id) {
        WorkflowInstance inst = instanceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkflowInstance not found with id " + id));
        return toDto(inst);
    }

    @Override
    public WorkflowInstanceDTO create(WorkflowInstanceDTO dto) {
        WorkflowDefinition def = definitionRepo.findById(dto.getDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException("WorkflowDefinition not found with id " + dto.getDefinitionId()));
        WorkflowState state = stateRepo.findById(dto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("WorkflowState not found with id " + dto.getStateId()));
        SecurityUser user = null;
        if (dto.getAssignedToUserId() != null) {
            user = userRepo.findById(dto.getAssignedToUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getAssignedToUserId()));
        }
        WorkflowInstance inst = new WorkflowInstance();
        inst.setWf(def);
        inst.setWfEntidadTipo(dto.getEntityType());
        inst.setWfEntidadId(dto.getEntityId());
        inst.setWfEst(state);
        inst.setWfAsignadoA(user);
        inst.setWfFechaInicio(dto.getStartDate());
        WorkflowInstance saved = instanceRepo.save(inst);
        return toDto(saved);
    }

    @Override
    public WorkflowInstanceDTO update(Integer id, WorkflowInstanceDTO dto) {
        WorkflowInstance inst = instanceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WorkflowInstance not found with id " + id));
        WorkflowDefinition def = definitionRepo.findById(dto.getDefinitionId())
                .orElseThrow(() -> new EntityNotFoundException("WorkflowDefinition not found with id " + dto.getDefinitionId()));
        WorkflowState state = stateRepo.findById(dto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("WorkflowState not found with id " + dto.getStateId()));
        SecurityUser user = null;
        if (dto.getAssignedToUserId() != null) {
            user = userRepo.findById(dto.getAssignedToUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getAssignedToUserId()));
        }
        inst.setWf(def);
        inst.setWfEntidadTipo(dto.getEntityType());
        inst.setWfEntidadId(dto.getEntityId());
        inst.setWfEst(state);
        inst.setWfAsignadoA(user);
        inst.setWfFechaInicio(dto.getStartDate());
        WorkflowInstance updated = instanceRepo.save(inst);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!instanceRepo.existsById(id)) {
            throw new EntityNotFoundException("WorkflowInstance not found with id " + id);
        }
        instanceRepo.deleteById(id);
    }

    private WorkflowInstanceDTO toDto(WorkflowInstance inst) {
        Integer userId = inst.getWfAsignadoA() != null ? inst.getWfAsignadoA().getId() : null;
        return WorkflowInstanceDTO.builder()
                .id(inst.getId())
                .definitionId(inst.getWf().getId())
                .entityType(inst.getWfEntidadTipo())
                .entityId(inst.getWfEntidadId())
                .stateId(inst.getWfEst().getId())
                .assignedToUserId(userId)
                .startDate(inst.getWfFechaInicio())
                .build();
    }
}
