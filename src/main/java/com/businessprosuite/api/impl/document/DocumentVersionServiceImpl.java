package com.businessprosuite.api.impl.document;

import com.businessprosuite.api.dto.document.DocumentVersionDTO;
import com.businessprosuite.api.model.document.DocumentVersion;
import com.businessprosuite.api.model.document.Document;
import com.businessprosuite.api.repository.document.DocumentVersionRepository;
import com.businessprosuite.api.repository.document.DocumentRepository;
import com.businessprosuite.api.service.document.DocumentVersionService;
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
public class DocumentVersionServiceImpl implements DocumentVersionService {

    private final DocumentVersionRepository versionRepo;
    private final DocumentRepository docRepo;

    @Override
    public List<DocumentVersionDTO> findAll() {
        return versionRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentVersionDTO findById(Integer id) {
        DocumentVersion ver = versionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DocumentVersion not found with id " + id));
        return toDto(ver);
    }

    @Override
    public DocumentVersionDTO create(DocumentVersionDTO dto) {
        Document doc = docRepo.findById(dto.getDocumentId())
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + dto.getDocumentId()));

        DocumentVersion ver = new DocumentVersion();
        ver.setDoc(doc);
        ver.setVersionNumber(dto.getVersionNumber());
        ver.setUrl(dto.getUrl());
        ver.setCreatedAt(LocalDateTime.now());
        DocumentVersion saved = versionRepo.save(ver);
        return toDto(saved);
    }

    @Override
    public DocumentVersionDTO update(Integer id, DocumentVersionDTO dto) {
        DocumentVersion ver = versionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DocumentVersion not found with id " + id));
        Document doc = docRepo.findById(dto.getDocumentId())
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + dto.getDocumentId()));

        ver.setDoc(doc);
        ver.setVersionNumber(dto.getVersionNumber());
        ver.setUrl(dto.getUrl());
        // createdAt not changed
        DocumentVersion updated = versionRepo.save(ver);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!versionRepo.existsById(id)) {
            throw new EntityNotFoundException("DocumentVersion not found with id " + id);
        }
        versionRepo.deleteById(id);
    }

    private DocumentVersionDTO toDto(DocumentVersion ver) {
        return DocumentVersionDTO.builder()
                .id(ver.getId())
                .documentId(ver.getDoc().getId())
                .versionNumber(ver.getVersionNumber())
                .url(ver.getUrl())
                .createdAt(ver.getCreatedAt())
                .build();
    }
}