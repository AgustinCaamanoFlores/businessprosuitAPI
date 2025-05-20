package com.businessprosuite.api.impl.audit;

import com.businessprosuite.api.dto.audit.AuditTrailDTO;
import com.businessprosuite.api.model.audit.AuditTrail;
import com.businessprosuite.api.model.audit.AuditTrailId;
import com.businessprosuite.api.repository.audit.AuditTrailRepository;
import com.businessprosuite.api.service.audit.AuditTrailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditTrailServiceImpl implements AuditTrailService {

    private final AuditTrailRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<AuditTrailDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuditTrailDTO findById(AuditTrailId id) {
        AuditTrail at = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuditTrail not found with id " + id));
        return toDto(at);
    }

    @Override
    public AuditTrailDTO create(AuditTrailDTO dto) {
        AuditTrail at = new AuditTrail();
        at.setId(dto.getId());
        at.setAudaTable(dto.getTableName());
        at.setAudaRecordId(dto.getRecordId());
        at.setAudaAction(dto.getAction());
        at.setAudaChangedData(dto.getChangedData());
        at.setAudaChangedAt(dto.getChangedAt());
        at.setAudaIp(dto.getIp());
        at.setAudaUserId(dto.getUserId());
        at.setAudaUserAgent(dto.getUserAgent());
        at.setAudaCountry(dto.getCountry());
        AuditTrail saved = repo.save(at);
        return toDto(saved);
    }

    @Override
    public AuditTrailDTO update(AuditTrailId id, AuditTrailDTO dto) {
        AuditTrail at = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuditTrail not found with id " + id));
        at.setAudaTable(dto.getTableName());
        at.setAudaRecordId(dto.getRecordId());
        at.setAudaAction(dto.getAction());
        at.setAudaChangedData(dto.getChangedData());
        at.setAudaChangedAt(dto.getChangedAt());
        at.setAudaIp(dto.getIp());
        at.setAudaUserId(dto.getUserId());
        at.setAudaUserAgent(dto.getUserAgent());
        at.setAudaCountry(dto.getCountry());
        AuditTrail updated = repo.save(at);
        return toDto(updated);
    }

    @Override
    public void delete(AuditTrailId id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("AuditTrail not found with id " + id);
        }
        repo.deleteById(id);
    }

    private AuditTrailDTO toDto(AuditTrail at) {
        return AuditTrailDTO.builder()
                .id(at.getId())
                .tableName(at.getAudaTable())
                .recordId(at.getAudaRecordId())
                .action(at.getAudaAction())
                .changedData(at.getAudaChangedData())
                .changedAt(at.getAudaChangedAt())
                .ip(at.getAudaIp())
                .userId(at.getAudaUserId())
                .userAgent(at.getAudaUserAgent())
                .country(at.getAudaCountry())
                .build();
    }
}