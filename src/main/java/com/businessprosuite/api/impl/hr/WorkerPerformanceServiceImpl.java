package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.WorkerPerformanceDTO;
import com.businessprosuite.api.model.hr.WorkerPerformance;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.repository.hr.WorkerPerformanceRepository;
import com.businessprosuite.api.repository.hr.WorkerRepository;
import com.businessprosuite.api.service.hr.WorkerPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerPerformanceServiceImpl implements WorkerPerformanceService {

    private final WorkerPerformanceRepository repo;
    private final WorkerRepository               workerRepo;

    public WorkerPerformanceServiceImpl(WorkerPerformanceRepository repo,
                                        WorkerRepository workerRepo) {
        this.repo = repo;
        this.workerRepo = workerRepo;
    }

    @Override
    public List<WorkerPerformanceDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerPerformanceDTO findById(Integer id) {
        WorkerPerformance e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerPerformance no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public WorkerPerformanceDTO create(WorkerPerformanceDTO dto) {
        WorkerPerformance e = toEntity(dto);
        WorkerPerformance saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public WorkerPerformanceDTO update(Integer id, WorkerPerformanceDTO dto) {
        WorkerPerformance existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "WorkerPerformance no encontrado: " + id));
        if (!existing.getHrW().getId().equals(dto.getWorkerId())) {
            Worker w = workerRepo.findById(dto.getWorkerId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Worker no encontrado: " + dto.getWorkerId()));
            existing.setHrW(w);
        }
        existing.setEvalDate(dto.getEvalDate());
        existing.setScore(dto.getScore());
        existing.setRemarks(dto.getRemarks());
        WorkerPerformance updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private WorkerPerformanceDTO toDto(WorkerPerformance e) {
        return WorkerPerformanceDTO.builder()
                .id(e.getId())
                .workerId(e.getHrW().getId())
                .evalDate(e.getEvalDate())
                .score(e.getScore())
                .remarks(e.getRemarks())
                .build();
    }

    private WorkerPerformance toEntity(WorkerPerformanceDTO d) {
        WorkerPerformance e = new WorkerPerformance();
        Worker w = workerRepo.findById(d.getWorkerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Worker no encontrado: " + d.getWorkerId()));
        e.setHrW(w);
        e.setEvalDate(d.getEvalDate());
        e.setScore(d.getScore());
        e.setRemarks(d.getRemarks());
        return e;
    }
}
