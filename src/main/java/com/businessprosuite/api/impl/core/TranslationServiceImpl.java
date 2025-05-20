package com.businessprosuite.api.impl.core;

import com.businessprosuite.api.dto.core.TranslationDTO;
import com.businessprosuite.api.model.core.Translation;
import com.businessprosuite.api.repository.inventory.InventoryTranslationRepository;
import com.businessprosuite.api.service.core.TranslationService;
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
public class TranslationServiceImpl implements TranslationService {

    private final InventoryTranslationRepository repo;

    @Override
    public List<TranslationDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TranslationDTO findById(Integer id) {
        Translation entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found with id " + id));
        return toDto(entity);
    }

    @Override
    public TranslationDTO create(TranslationDTO dto) {
        Translation tr = new Translation();
        tr.setIntLang(dto.getLang());
        tr.setIntKey(dto.getKey());
        tr.setIntValue(dto.getValue());
        tr.setIntCreatedAt(LocalDateTime.now());
        tr.setIntUpdatedAt(LocalDateTime.now());
        Translation saved = repo.save(tr);
        return toDto(saved);
    }

    @Override
    public TranslationDTO update(Integer id, TranslationDTO dto) {
        Translation tr = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found with id " + id));
        tr.setIntLang(dto.getLang());
        tr.setIntKey(dto.getKey());
        tr.setIntValue(dto.getValue());
        tr.setIntUpdatedAt(LocalDateTime.now());
        Translation updated = repo.save(tr);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Translation not found with id " + id);
        }
        repo.deleteById(id);
    }

    private TranslationDTO toDto(Translation tr) {
        return TranslationDTO.builder()
                .id(tr.getId())
                .lang(tr.getIntLang())
                .key(tr.getIntKey())
                .value(tr.getIntValue())
                .createdAt(tr.getIntCreatedAt())
                .updatedAt(tr.getIntUpdatedAt())
                .build();
    }
}