package com.businessprosuite.api.impl.schema;

import com.businessprosuite.api.dto.schema.SchemaVersionDTO;
import com.businessprosuite.api.model.schema.SchemaVersion;
import com.businessprosuite.api.repository.schema.SchemaVersionRepository;
import com.businessprosuite.api.service.schema.SchemaVersionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SchemaVersionServiceImpl implements SchemaVersionService {

    private final SchemaVersionRepository repo;

    @Override
    public List<SchemaVersionDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchemaVersionDTO findById(Integer id) {
        SchemaVersion entry = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchemaVersion not found with id " + id));
        return toDto(entry);
    }

    @Override
    public SchemaVersionDTO create(SchemaVersionDTO dto) {
        SchemaVersion entry = new SchemaVersion();
        entry.setSchVersion(dto.getVersion());
        entry.setSchAppliedAt(dto.getAppliedAt() != null ? dto.getAppliedAt() : LocalDateTime.now());
        SchemaVersion saved = repo.save(entry);
        return toDto(saved);
    }

    @Override
    public SchemaVersionDTO update(Integer id, SchemaVersionDTO dto) {
        SchemaVersion entry = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchemaVersion not found with id " + id));
        entry.setSchVersion(dto.getVersion());
        // appliedAt not updated to preserve SensitiveDataAccessDTO
        SchemaVersion updated = repo.save(entry);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SchemaVersion not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SchemaVersionDTO toDto(SchemaVersion entry) {
        return SchemaVersionDTO.builder()
                .id(entry.getId())
                .version(entry.getSchVersion())
                .appliedAt(entry.getSchAppliedAt())
                .build();
    }
}
