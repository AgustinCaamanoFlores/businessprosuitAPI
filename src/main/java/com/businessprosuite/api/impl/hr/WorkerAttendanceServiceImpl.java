package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.WorkerAttendanceDTO;
import com.businessprosuite.api.model.hr.WorkerAttendance;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.model.hr.Shift;
import com.businessprosuite.api.repository.hr.WorkerAttendanceRepository;
import com.businessprosuite.api.repository.hr.WorkerRepository;
import com.businessprosuite.api.repository.hr.ShiftRepository;
import com.businessprosuite.api.service.hr.WorkerAttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerAttendanceServiceImpl implements WorkerAttendanceService {

    private final WorkerAttendanceRepository repo;
    private final WorkerRepository           workerRepo;
    private final ShiftRepository            shiftRepo;

    public WorkerAttendanceServiceImpl(WorkerAttendanceRepository repo,
                                       WorkerRepository workerRepo,
                                       ShiftRepository shiftRepo) {
        this.repo = repo;
        this.workerRepo = workerRepo;
        this.shiftRepo = shiftRepo;
    }

    @Override
    public List<WorkerAttendanceDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerAttendanceDTO findById(Integer id) {
        WorkerAttendance e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerAttendance no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public WorkerAttendanceDTO create(WorkerAttendanceDTO dto) {
        WorkerAttendance e = toEntity(dto);
        WorkerAttendance saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public WorkerAttendanceDTO update(Integer id, WorkerAttendanceDTO dto) {
        WorkerAttendance existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerAttendance no encontrado: " + id));

        if (!existing.getHrAttHrW().getId().equals(dto.getWorkerId())) {
            Worker w = workerRepo.findById(dto.getWorkerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Worker no encontrado: " + dto.getWorkerId()));
            existing.setHrAttHrW(w);
        }
        if (!existing.getHrAttShift().getId().equals(dto.getShiftId())) {
            Shift s = shiftRepo.findById(dto.getShiftId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Shift no encontrado: " + dto.getShiftId()));
            existing.setHrAttShift(s);
        }
        existing.setHrAttTime(dto.getTime());
        existing.setHrAttType(dto.getType());
        existing.setHrAttMethod(dto.getMethod());
        existing.setHrAttDeviceId(dto.getDeviceId());
        // createdAt permanece inmutable
        WorkerAttendance updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private WorkerAttendanceDTO toDto(WorkerAttendance e) {
        return WorkerAttendanceDTO.builder()
                .id(e.getId())
                .workerId(e.getHrAttHrW().getId())
                .shiftId(e.getHrAttShift().getId())
                .time(e.getHrAttTime())
                .type(e.getHrAttType())
                .method(e.getHrAttMethod())
                .deviceId(e.getHrAttDeviceId())
                .createdAt(e.getHrAttCreatedAt())
                .build();
    }

    private WorkerAttendance toEntity(WorkerAttendanceDTO d) {
        WorkerAttendance e = new WorkerAttendance();
        Worker w = workerRepo.findById(d.getWorkerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Worker no encontrado: " + d.getWorkerId()));
        Shift s = shiftRepo.findById(d.getShiftId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Shift no encontrado: " + d.getShiftId()));
        e.setHrAttHrW(w);
        e.setHrAttShift(s);
        e.setHrAttTime(d.getTime());
        e.setHrAttType(d.getType());
        e.setHrAttMethod(d.getMethod());
        e.setHrAttDeviceId(d.getDeviceId());
        // hrAttCreatedAt lo maneja la BD
        return e;
    }
}
