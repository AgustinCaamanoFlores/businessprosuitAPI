package com.businessprosuite.api.impl.hr;

import com.businessprosuite.api.dto.hr.CompanyDeviceDTO;
import com.businessprosuite.api.model.hr.CompanyDevice;
import com.businessprosuite.api.repository.hr.CompanyDeviceRepository;
import com.businessprosuite.api.service.hr.CompanyDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyDeviceServiceImpl implements CompanyDeviceService {

    private final CompanyDeviceRepository repo;

    public CompanyDeviceServiceImpl(CompanyDeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CompanyDeviceDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDeviceDTO findById(Integer id) {
        CompanyDevice e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "CompanyDevice no encontrado: " + id));
        return toDto(e);
    }

    @Override
    public CompanyDeviceDTO create(CompanyDeviceDTO dto) {
        CompanyDevice e = toEntity(dto);
        CompanyDevice saved = repo.save(e);
        return toDto(saved);
    }

    @Override
    public CompanyDeviceDTO update(Integer id, CompanyDeviceDTO dto) {
        CompanyDevice existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "CompanyDevice no encontrado: " + id));
        existing.setHrDeviceLocation(dto.getLocation());
        existing.setDeviceType(dto.getType());
        existing.setHrDeviceActive(dto.getActive());
        CompanyDevice updated = repo.save(existing);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private CompanyDeviceDTO toDto(CompanyDevice e) {
        return CompanyDeviceDTO.builder()
                .id(e.getId())
                .location(e.getHrDeviceLocation())
                .type(e.getDeviceType())
                .active(e.getHrDeviceActive())
                .build();
    }

    private CompanyDevice toEntity(CompanyDeviceDTO d) {
        CompanyDevice e = new CompanyDevice();
        e.setHrDeviceLocation(d.getLocation());
        e.setDeviceType(d.getType());
        e.setHrDeviceActive(d.getActive());
        return e;
    }
}
