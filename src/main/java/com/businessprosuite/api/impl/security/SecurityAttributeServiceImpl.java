package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityAttributeDTO;
import com.businessprosuite.api.model.security.SecurityAttribute;
import com.businessprosuite.api.repository.security.SecurityAttributeRepository;
import com.businessprosuite.api.service.security.SecurityAttributeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityAttributeServiceImpl implements SecurityAttributeService {

    private final SecurityAttributeRepository repo;

    @Override
    public List<SecurityAttributeDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityAttributeDTO findById(Integer id) {
        SecurityAttribute attr = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityAttribute not found with id " + id));
        return toDto(attr);
    }

    @Override
    public SecurityAttributeDTO create(SecurityAttributeDTO dto) {
        SecurityAttribute attr = new SecurityAttribute();
        attr.setName(dto.getName());
        attr.setDescription(dto.getDescription());
        SecurityAttribute saved = repo.save(attr);
        return toDto(saved);
    }

    @Override
    public SecurityAttributeDTO update(Integer id, SecurityAttributeDTO dto) {
        SecurityAttribute attr = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityAttribute not found with id " + id));
        attr.setName(dto.getName());
        attr.setDescription(dto.getDescription());
        SecurityAttribute updated = repo.save(attr);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("SecurityAttribute not found with id " + id);
        }
        repo.deleteById(id);
    }

    private SecurityAttributeDTO toDto(SecurityAttribute attr) {
        return SecurityAttributeDTO.builder()
                .id(attr.getId())
                .name(attr.getName())
                .description(attr.getDescription())
                .build();
    }
}
