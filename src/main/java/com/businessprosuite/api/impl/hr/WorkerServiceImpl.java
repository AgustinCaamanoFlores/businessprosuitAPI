package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.WorkerDTO;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.model.hr.WorkerDepartment;
import com.businessprosuite.api.model.hr.Shift;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.hr.WorkerRepository;
import com.businessprosuite.api.repository.hr.WorkerDepartmentRepository;
import com.businessprosuite.api.repository.hr.ShiftRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.hr.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository                  repo;
    private final WorkerDepartmentRepository        deptRepo;
    private final ShiftRepository                   shiftRepo;
    private final CompanyRepository                 companyRepo;

    public WorkerServiceImpl(WorkerRepository repo,
                             WorkerDepartmentRepository deptRepo,
                             ShiftRepository shiftRepo,
                             CompanyRepository companyRepo) {
        this.repo = repo;
        this.deptRepo = deptRepo;
        this.shiftRepo = shiftRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<WorkerDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerDTO findById(Integer id) {
        Worker e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Worker no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public WorkerDTO create(WorkerDTO dto) {
        Worker e = toEntity(dto);
        Worker saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public WorkerDTO update(Integer id, WorkerDTO dto) {
        Worker existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Worker no encontrado: " + id));

        existing.setHrWName(dto.getName());
        existing.setHrWAddress(dto.getAddress());
        existing.setHrWCell(dto.getCell());
        existing.setHrWHomePhone(dto.getHomePhone());
        existing.setHrWEmail(dto.getEmail());
        existing.setHrWBirthDate(dto.getBirthDate());
        if (!existing.getWorkerDepartment().getId().equals(dto.getDepartmentId())) {
            WorkerDepartment wd = deptRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "WorkerDepartment no encontrada: " + dto.getDepartmentId()));
            existing.setWorkerDepartment(wd);
        }
        if (!existing.getShift().getId().equals(dto.getShiftId())) {
            Shift s = shiftRepo.findById(dto.getShiftId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Shift no encontrado: " + dto.getShiftId()));
            existing.setShift(s);
        }
        if (!existing.getHrCmp().getId().equals(dto.getCompanyId())) {
            Company c = companyRepo.findById(dto.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Company no encontrada: " + dto.getCompanyId()));
            existing.setHrCmp(c);
        }
        existing.setHrWDeleted(dto.getDeleted());
        Worker updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private WorkerDTO toDto(Worker e) {
        return WorkerDTO.builder()
                .id(e.getId())
                .name(e.getHrWName())
                .address(e.getHrWAddress())
                .cell(e.getHrWCell())
                .homePhone(e.getHrWHomePhone())
                .email(e.getHrWEmail())
                .birthDate(e.getHrWBirthDate())
                .departmentId(e.getWorkerDepartment().getId())
                .shiftId(e.getShift().getId())
                .companyId(e.getHrCmp().getId())
                .deleted(e.getHrWDeleted())
                .createdAt(e.getHrCreatedAt())
                .updatedAt(e.getHrUpdatedAt())
                .build();
    }

    private Worker toEntity(WorkerDTO d) {
        Worker e = new Worker();
        e.setHrWName(d.getName());
        e.setHrWAddress(d.getAddress());
        e.setHrWCell(d.getCell());
        e.setHrWHomePhone(d.getHomePhone());
        e.setHrWEmail(d.getEmail());
        e.setHrWBirthDate(d.getBirthDate());

        WorkerDepartment wd = deptRepo.findById(d.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerDepartment no encontrada: " + d.getDepartmentId()));
        e.setWorkerDepartment(wd);

        Shift s = shiftRepo.findById(d.getShiftId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Shift no encontrado: " + d.getShiftId()));
        e.setShift(s);

        Company c = companyRepo.findById(d.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Company no encontrada: " + d.getCompanyId()));
        e.setHrCmp(c);

        e.setHrWDeleted(d.getDeleted());
        // hrCreatedAt y hrUpdatedAt se gestionan en BD
        return e;
    }
}
