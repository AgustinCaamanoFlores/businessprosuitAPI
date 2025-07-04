package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.PayrollDTO;
import com.businessprosuite.api.model.hr.Payroll;
import com.businessprosuite.api.model.hr.Worker;
import com.businessprosuite.api.repository.hr.PayrollRepository;
import com.businessprosuite.api.repository.hr.WorkerRepository;
import com.businessprosuite.api.service.hr.PayrollService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository repo;
    private final WorkerRepository workerRepo;

    public PayrollServiceImpl(PayrollRepository repo, WorkerRepository workerRepo) {
        this.repo = repo;
        this.workerRepo = workerRepo;
    }

    @Override
    public List<PayrollDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PayrollDTO findById(Integer id) {
        Payroll e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payroll no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public PayrollDTO create(PayrollDTO dto) {
        Payroll e = toEntity(dto);
        Payroll saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public PayrollDTO update(Integer id, PayrollDTO dto) {
        Payroll existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payroll no encontrado: " + id));
        if (!existing.getWorker().getId().equals(dto.getWorkerId())) {
            Worker w = workerRepo.findById(dto.getWorkerId())
                    .orElseThrow(() -> new IllegalArgumentException("Worker no encontrado: " + dto.getWorkerId()));
            existing.setWorker(w);
        }
        existing.setBaseSalary(dto.getBaseSalary());
        existing.setBonus(dto.getBonus());
        existing.setDeductions(dto.getDeductions());
        existing.setPayDate(dto.getPayDate());
        existing.setRemarks(dto.getRemarks());
        Payroll updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private PayrollDTO toDto(Payroll e) {
        return PayrollDTO.builder()
                .id(e.getId())
                .workerId(e.getWorker().getId())
                .baseSalary(e.getBaseSalary())
                .bonus(e.getBonus())
                .deductions(e.getDeductions())
                .payDate(e.getPayDate())
                .remarks(e.getRemarks())
                .build();
    }

    private Payroll toEntity(PayrollDTO d) {
        Payroll e = new Payroll();
        Worker w = workerRepo.findById(d.getWorkerId())
                .orElseThrow(() -> new IllegalArgumentException("Worker no encontrado: " + d.getWorkerId()));
        e.setWorker(w);
        e.setBaseSalary(d.getBaseSalary());
        e.setBonus(d.getBonus());
        e.setDeductions(d.getDeductions());
        e.setPayDate(d.getPayDate());
        e.setRemarks(d.getRemarks());
        return e;
    }
}
