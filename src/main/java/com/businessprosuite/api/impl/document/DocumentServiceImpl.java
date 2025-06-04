package com.businessprosuite.api.impl.document;

import com.businessprosuite.api.dto.document.DocumentDTO;
import com.businessprosuite.api.model.document.Document;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.repository.document.DocumentRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.service.document.DocumentService;
import com.businessprosuite.api.mapper.DocumentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepo;
    private final CompanyRepository companyRepo;
    private final DocumentMapper documentMapper;

    @Override
    @Cacheable("documents")
    public List<DocumentDTO> findAll() {
        return docRepo.findAll().stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDTO findById(Integer id) {
        Document doc = docRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + id));
        return documentMapper.toDto(doc);
    }

    @Override
    public DocumentDTO create(DocumentDTO dto) {
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));
        Document doc = documentMapper.toEntity(dto);
        doc.setDocCmp(cmp);
        doc.setDocCreatedAt(LocalDateTime.now());
        Document saved = docRepo.save(doc);
        return documentMapper.toDto(saved);
    }

    @Override
    public DocumentDTO update(Integer id, DocumentDTO dto) {
        Document doc = docRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id " + id));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));
        documentMapper.updateEntity(dto, doc);
        doc.setDocCmp(cmp);
        // createdAt unchanged
        Document updated = docRepo.save(doc);
        return documentMapper.toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!docRepo.existsById(id)) {
            throw new EntityNotFoundException("Document not found with id " + id);
        }
        docRepo.deleteById(id);
    }

}
