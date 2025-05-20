package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowTimerDTO;
import com.businessprosuite.api.model.workflow.WorkflowTimer;
import com.businessprosuite.api.model.workflow.WorkflowInstance;
import com.businessprosuite.api.repository.workflow.WorkflowTimerRepository;
import com.businessprosuite.api.repository.workflow.WorkflowInstanceRepository;
import com.businessprosuite.api.service.workflow.WorkflowTimerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowTimerServiceImpl implements WorkflowTimerService {

    private final WorkflowTimerRepository timerRepo;
    private final WorkflowInstanceRepository instanceRepo;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowTimerDTO> findAll() {
        return timerRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowTimerDTO findById(Integer id) {
        WorkflowTimer timer = timerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowTimer not found with id " + id));
        return toDto(timer);
    }

    @Override
    public WorkflowTimerDTO create(WorkflowTimerDTO dto) {
        WorkflowInstance inst = instanceRepo.findById(dto.getWorkflowInstanceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowInstance not found with id " + dto.getWorkflowInstanceId()));
        WorkflowTimer timer = new WorkflowTimer();
        timer.setWfIns(inst);
        timer.setDueAt(dto.getDueAt());
        timer.setAction(dto.getAction());
        timer.setExecuted(dto.getExecuted() != null ? dto.getExecuted() : false);
        WorkflowTimer saved = timerRepo.save(timer);
        return toDto(saved);
    }

    @Override
    public WorkflowTimerDTO update(Integer id, WorkflowTimerDTO dto) {
        WorkflowTimer timer = timerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowTimer not found with id " + id));
        WorkflowInstance inst = instanceRepo.findById(dto.getWorkflowInstanceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowInstance not found with id " + dto.getWorkflowInstanceId()));
        timer.setWfIns(inst);
        timer.setDueAt(dto.getDueAt());
        timer.setAction(dto.getAction());
        timer.setExecuted(dto.getExecuted());
        WorkflowTimer updated = timerRepo.save(timer);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!timerRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    "WorkflowTimer not found with id " + id);
        }
        timerRepo.deleteById(id);
    }

    private WorkflowTimerDTO toDto(WorkflowTimer timer) {
        return WorkflowTimerDTO.builder()
                .id(timer.getId())
                .workflowInstanceId(timer.getWfIns().getId())
                .dueAt(timer.getDueAt())
                .action(timer.getAction())
                .executed(timer.getExecuted())
                .build();
    }
}