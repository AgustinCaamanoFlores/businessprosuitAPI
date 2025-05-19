package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.LeaveTypeDTO;
import com.businessprosuite.api.model.hr.LeaveType;
import com.businessprosuite.api.repository.hr.LeaveTypeRepository;
import com.businessprosuite.api.service.hr.LeaveTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository repo;

    public LeaveTypeServiceImpl(LeaveTypeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<LeaveTypeDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveTypeDTO findById(Integer id) {
        LeaveType e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "LeaveType no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public LeaveTypeDTO create(LeaveTypeDTO dto) {
        LeaveType e = toEntity(dto);
        LeaveType saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public LeaveTypeDTO update(Integer id, LeaveTypeDTO dto) {
        LeaveType existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "LeaveType no encontrado: " + id));
        existing.setName(dto.getName());
        existing.setMaxDays(dto.getMaxDays());
        LeaveType updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private LeaveTypeDTO toDto(LeaveType e) {
        return LeaveTypeDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .maxDays(e.getMaxDays())
                .build();
    }

    private LeaveType toEntity(LeaveTypeDTO d) {
        LeaveType e = new LeaveType();
        e.setName(d.getName());
        e.setMaxDays(d.getMaxDays());
        return e;
    }
}
