package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.LeaveRequestDTO;
import com.businessprosuite.api.model.hr.LeaveRequest;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.model.hr.LeaveType;
import com.businessprosuite.api.repository.hr.LeaveRequestRepository;
import com.businessprosuite.api.repository.hr.WorkerRepository;
import com.businessprosuite.api.repository.hr.LeaveTypeRepository;
import com.businessprosuite.api.service.hr.LeaveRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository   repo;
    private final WorkerRepository         workerRepo;
    private final LeaveTypeRepository      typeRepo;

    public LeaveRequestServiceImpl(LeaveRequestRepository repo,
                                   WorkerRepository workerRepo,
                                   LeaveTypeRepository typeRepo) {
        this.repo = repo;
        this.workerRepo = workerRepo;
        this.typeRepo = typeRepo;
    }

    @Override
    public List<LeaveRequestDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDTO findById(Integer id) {
        LeaveRequest e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "LeaveRequest no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public LeaveRequestDTO create(LeaveRequestDTO dto) {
        LeaveRequest e = toEntity(dto);
        LeaveRequest saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public LeaveRequestDTO update(Integer id, LeaveRequestDTO dto) {
        LeaveRequest existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "LeaveRequest no encontrado: " + id));

        if (!existing.getHrW().getId().equals(dto.getWorkerId())) {
            Worker w = workerRepo.findById(dto.getWorkerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Worker no encontrado: " + dto.getWorkerId()));
            existing.setHrW(w);
        }
        if (!existing.getLt().getId().equals(dto.getLeaveTypeId())) {
            LeaveType lt = typeRepo.findById(dto.getLeaveTypeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "LeaveType no encontrado: " + dto.getLeaveTypeId()));
            existing.setLt(lt);
        }
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setStatus(dto.getStatus());
        // appliedAt permanece inmutable tras la creación

        LeaveRequest updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private LeaveRequestDTO toDto(LeaveRequest e) {
        return LeaveRequestDTO.builder()
                .id(e.getId())
                .workerId(e.getHrW().getId())
                .leaveTypeId(e.getLt().getId())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .status(e.getStatus())
                .appliedAt(e.getAppliedAt())
                .build();
    }

    private LeaveRequest toEntity(LeaveRequestDTO d) {
        LeaveRequest e = new LeaveRequest();
        Worker w = workerRepo.findById(d.getWorkerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Worker no encontrado: " + d.getWorkerId()));
        LeaveType lt = typeRepo.findById(d.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "LeaveType no encontrado: " + d.getLeaveTypeId()));

        e.setHrW(w);
        e.setLt(lt);
        e.setStartDate(d.getStartDate());
        e.setEndDate(d.getEndDate());
        e.setStatus(d.getStatus());
        // appliedAt será asignado por defecto en BD
        return e;
    }
}
