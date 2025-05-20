package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowHistoryDTO;
import com.businessprosuite.api.model.workflow.WorkflowHistory;
import com.businessprosuite.api.model.workflow.WorkflowInstance;
import com.businessprosuite.api.repository.workflow.WorkflowHistoryRepository;
import com.businessprosuite.api.repository.workflow.WorkflowInstanceRepository;
import com.businessprosuite.api.service.workflow.WorkflowHistoryService;
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
public class WorkflowHistoryServiceImpl implements WorkflowHistoryService {

    private final WorkflowHistoryRepository historyRepo;
    private final WorkflowInstanceRepository instanceRepo;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowHistoryDTO> findAll() {
        return historyRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowHistoryDTO findById(Integer id) {
        WorkflowHistory hist = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowHistory not found with id " + id));
        return toDto(hist);
    }

    @Override
    public WorkflowHistoryDTO create(WorkflowHistoryDTO dto) {
        WorkflowInstance inst = instanceRepo.findById(dto.getWorkflowInstanceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowInstance not found with id " + dto.getWorkflowInstanceId()));

        WorkflowHistory hist = new WorkflowHistory();
        hist.setWfIns(inst);
        hist.setWfDe(dto.getFromState());
        hist.setWfA(dto.getToState());
        hist.setWfFecha(dto.getEventDate() != null ? dto.getEventDate() : LocalDateTime.now());
        hist.setWfUsuario(dto.getUserId());
        WorkflowHistory saved = historyRepo.save(hist);
        return toDto(saved);
    }

    @Override
    public WorkflowHistoryDTO update(Integer id, WorkflowHistoryDTO dto) {
        WorkflowHistory hist = historyRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowHistory not found with id " + id));
        WorkflowInstance inst = instanceRepo.findById(dto.getWorkflowInstanceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowInstance not found with id " + dto.getWorkflowInstanceId()));
        hist.setWfIns(inst);
        hist.setWfDe(dto.getFromState());
        hist.setWfA(dto.getToState());
        // preserve original event date if dto.eventDate is null
        if (dto.getEventDate() != null) {
            hist.setWfFecha(dto.getEventDate());
        }
        hist.setWfUsuario(dto.getUserId());
        WorkflowHistory updated = historyRepo.save(hist);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!historyRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    "WorkflowHistory not found with id " + id);
        }
        historyRepo.deleteById(id);
    }

    private WorkflowHistoryDTO toDto(WorkflowHistory hist) {
        return WorkflowHistoryDTO.builder()
                .id(hist.getId())
                .workflowInstanceId(hist.getWfIns().getId())
                .fromState(hist.getWfDe())
                .toState(hist.getWfA())
                .eventDate(hist.getWfFecha())
                .userId(hist.getWfUsuario())
                .build();
    }
}
