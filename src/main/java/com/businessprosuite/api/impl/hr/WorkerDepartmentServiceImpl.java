package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.WorkerDepartmentDTO;
import com.businessprosuite.api.model.hr.WorkerDepartment;
import com.businessprosuite.api.repository.hr.WorkerDepartmentRepository;
import com.businessprosuite.api.service.hr.WorkerDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerDepartmentServiceImpl implements WorkerDepartmentService {

    private final WorkerDepartmentRepository repo;

    public WorkerDepartmentServiceImpl(WorkerDepartmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<WorkerDepartmentDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerDepartmentDTO findById(Integer id) {
        WorkerDepartment e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerDepartment no encontrada: " + id));
        return toDto(e);
    }

    @Override
    public WorkerDepartmentDTO create(WorkerDepartmentDTO dto) {
        WorkerDepartment e = toEntity(dto);
        WorkerDepartment saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public WorkerDepartmentDTO update(Integer id, WorkerDepartmentDTO dto) {
        WorkerDepartment existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerDepartment no encontrada: " + id));
        existing.setHrDeptName(dto.getName());
        WorkerDepartment updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private WorkerDepartmentDTO toDto(WorkerDepartment e) {
        return WorkerDepartmentDTO.builder()
                .id(e.getId())
                .name(e.getHrDeptName())
                .createdAt(e.getHrDeptCreatedAt())
                .build();
    }

    private WorkerDepartment toEntity(WorkerDepartmentDTO d) {
        WorkerDepartment e = new WorkerDepartment();
        e.setHrDeptName(d.getName());
        // hrDeptCreatedAt lo maneja la BD
        return e;
    }
}
