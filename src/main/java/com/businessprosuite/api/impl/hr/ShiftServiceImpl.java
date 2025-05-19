package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.ShiftDTO;
import com.businessprosuite.api.model.hr.Shift;
import com.businessprosuite.api.repository.hr.ShiftRepository;
import com.businessprosuite.api.service.hr.ShiftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository repo;

    public ShiftServiceImpl(ShiftRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ShiftDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftDTO findById(Integer id) {
        Shift e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shift no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public ShiftDTO create(ShiftDTO dto) {
        Shift e = toEntity(dto);
        Shift saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public ShiftDTO update(Integer id, ShiftDTO dto) {
        Shift existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shift no encontrado: " + id));
        existing.setHrShiftName(dto.getName());
        existing.setHrShiftStartTime(dto.getStartTime());
        existing.setHrShiftEndTime(dto.getEndTime());
        Shift updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private ShiftDTO toDto(Shift e) {
        return ShiftDTO.builder()
                .id(e.getId())
                .name(e.getHrShiftName())
                .startTime(e.getHrShiftStartTime())
                .endTime(e.getHrShiftEndTime())
                .createdAt(e.getHrShiftCreatedAt())
                .build();
    }

    private Shift toEntity(ShiftDTO d) {
        Shift e = new Shift();
        e.setHrShiftName(d.getName());
        e.setHrShiftStartTime(d.getStartTime());
        e.setHrShiftEndTime(d.getEndTime());
        // hrShiftCreatedAt lo maneja la BD
        return e;
    }
}
