package com.businessprosuite.api.impl.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionDTO;
import com.businessprosuite.api.model.workflow.WorkflowDefinition;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.repository.workflow.WorkflowDefinitionRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.service.workflow.WorkflowDefinitionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    private final WorkflowDefinitionRepository workflowDefinitionRepository;
    private final ConfigCompanyRepository configCompanyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowDefinitionDTO> findAll() {
        return workflowDefinitionRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowDefinitionDTO findById(Integer id) {
        WorkflowDefinition wf = workflowDefinitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + id));
        return toDto(wf);
    }

    @Override
    public WorkflowDefinitionDTO create(WorkflowDefinitionDTO dto) {
        ConfigCompany company = configCompanyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ConfigCompany not found with id " + dto.getCompanyId()));
        WorkflowDefinition wf = new WorkflowDefinition();
        wf.setWfNombre(dto.getName());
        wf.setConfigCompany(company);
        WorkflowDefinition saved = workflowDefinitionRepository.save(wf);
        return toDto(saved);
    }

    @Override
    public WorkflowDefinitionDTO update(Integer id, WorkflowDefinitionDTO dto) {
        WorkflowDefinition wf = workflowDefinitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "WorkflowDefinition not found with id " + id));
        ConfigCompany company = configCompanyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ConfigCompany not found with id " + dto.getCompanyId()));
        wf.setWfNombre(dto.getName());
        wf.setConfigCompany(company);
        WorkflowDefinition updated = workflowDefinitionRepository.save(wf);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!workflowDefinitionRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "WorkflowDefinition not found with id " + id);
        }
        workflowDefinitionRepository.deleteById(id);
    }

    private WorkflowDefinitionDTO toDto(WorkflowDefinition wf) {
        return WorkflowDefinitionDTO.builder()
                .id(wf.getId())
                .name(wf.getWfNombre())
                .companyId(wf.getConfigCompany().getId())
                .build();
    }
}