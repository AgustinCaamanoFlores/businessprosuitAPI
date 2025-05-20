package com.businessprosuite.api.impl.audit;

import com.businessprosuite.api.dto.audit.FieldChangeDTO;
import com.businessprosuite.api.model.audit.FieldChange;
import com.businessprosuite.api.repository.audit.FieldChangeRepository;
import com.businessprosuite.api.service.audit.FieldChangeService;
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
public class FieldChangeServiceImpl implements FieldChangeService {

    private final FieldChangeRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<FieldChangeDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FieldChangeDTO findById(Integer id) {
        FieldChange fc = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FieldChange not found with id " + id));
        return toDto(fc);
    }

    @Override
    public FieldChangeDTO create(FieldChangeDTO dto) {
        FieldChange fc = new FieldChange();
        fc.setTableName(dto.getTableName());
        fc.setPkValue(dto.getPkValue());
        fc.setFieldName(dto.getFieldName());
        fc.setOldValue(dto.getOldValue());
        fc.setNewValue(dto.getNewValue());
        fc.setChangedBy(dto.getChangedBy());
        fc.setChangedAt(dto.getChangedAt() != null ? dto.getChangedAt() : LocalDateTime.now());
        FieldChange saved = repo.save(fc);
        return toDto(saved);
    }

    @Override
    public FieldChangeDTO update(Integer id, FieldChangeDTO dto) {
        FieldChange fc = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FieldChange not found with id " + id));
        fc.setTableName(dto.getTableName());
        fc.setPkValue(dto.getPkValue());
        fc.setFieldName(dto.getFieldName());
        fc.setOldValue(dto.getOldValue());
        fc.setNewValue(dto.getNewValue());
        fc.setChangedBy(dto.getChangedBy());
        if (dto.getChangedAt() != null) {
            fc.setChangedAt(dto.getChangedAt());
        }
        FieldChange updated = repo.save(fc);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("FieldChange not found with id " + id);
        }
        repo.deleteById(id);
    }

    private FieldChangeDTO toDto(FieldChange fc) {
        return FieldChangeDTO.builder()
                .id(fc.getId())
                .tableName(fc.getTableName())
                .pkValue(fc.getPkValue())
                .fieldName(fc.getFieldName())
                .oldValue(fc.getOldValue())
                .newValue(fc.getNewValue())
                .changedBy(fc.getChangedBy())
                .changedAt(fc.getChangedAt())
                .build();
    }
}