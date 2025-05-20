package com.businessprosuite.api.impl.schema;

import com.businessprosuite.api.dto.schema.SchemaChangelogDTO;
import com.businessprosuite.api.model.schema.SchemaChangelog;
import com.businessprosuite.api.repository.schema.SchemaChangelogRepository;
import com.businessprosuite.api.service.schema.SchemaChangelogService;
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
public class SchemaChangelogServiceImpl implements SchemaChangelogService {

    private final SchemaChangelogRepository repo;

    @Override
    public List<SchemaChangelogDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchemaChangelogDTO findById(Integer id) {
        SchemaChangelog entry = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchemaChangelog not found with id " + id));
        return toDto(entry);
    }

    @Override
    public SchemaChangelogDTO create(SchemaChangelogDTO dto) {
        SchemaChangelog entry = new SchemaChangelog();
        entry.setSchChangeDescription(dto.getDescription());
        entry.setSchChangeDate(dto.getChangeDate() != null ? dto.getChangeDate() : LocalDateTime.now());
        entry.setSchAppliedBy(dto.getAppliedBy());
        entry.setSchChangeType(dto.getChangeType());
        entry.setSchAuthor(dto.getAuthor());
        entry.setSchComments(dto.getComments());
        SchemaChangelog saved = repo.save(entry);
        return toDto(saved);
    }

    @Override
    public SchemaChangelogDTO update(Integer id, SchemaChangelogDTO dto) {
        SchemaChangelog entry = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SchemaChangelog not found with id " + id));
        entry.setSchChangeDescription(dto.getDescription());
        entry.setSchAppliedBy(dto.getAppliedBy());
        entry.setSchChangeType(dto.getChangeType());
        entry.setSchAuthor(dto.getAuthor());
        entry.setSchComments(dto.getComments());
        // schChangeDate not updated
        SchemaChangelog updated = repo.save(entry);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SchemaChangelog not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SchemaChangelogDTO toDto(SchemaChangelog entry) {
        return SchemaChangelogDTO.builder()
                .id(entry.getId())
                .description(entry.getSchChangeDescription())
                .changeDate(entry.getSchChangeDate())
                .appliedBy(entry.getSchAppliedBy())
                .changeType(entry.getSchChangeType())
                .author(entry.getSchAuthor())
                .comments(entry.getSchComments())
                .build();
    }
}
