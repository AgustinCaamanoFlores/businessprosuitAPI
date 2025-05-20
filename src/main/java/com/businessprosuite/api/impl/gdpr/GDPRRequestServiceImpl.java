package com.businessprosuite.api.impl.gdpr;

import com.businessprosuite.api.dto.gdpr.GDPRRequestDTO;
import com.businessprosuite.api.model.gdpr.GDPRRequest;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.gdpr.GDPRRequestRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.gdpr.GDPRRequestService;
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
public class GDPRRequestServiceImpl implements GDPRRequestService {

    private final GDPRRequestRepository repo;
    private final SecurityUserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<GDPRRequestDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GDPRRequestDTO findById(Integer id) {
        GDPRRequest req = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GDPRRequest not found with id " + id));
        return toDto(req);
    }

    @Override
    public GDPRRequestDTO create(GDPRRequestDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        GDPRRequest req = new GDPRRequest();
        req.setSecus(user);
        req.setRequestType(dto.getRequestType());
        req.setStatus(dto.getStatus());
        req.setSubmittedAt(dto.getSubmittedAt() != null ? dto.getSubmittedAt() : LocalDateTime.now());
        req.setCompletedAt(dto.getCompletedAt());
        GDPRRequest saved = repo.save(req);
        return toDto(saved);
    }

    @Override
    public GDPRRequestDTO update(Integer id, GDPRRequestDTO dto) {
        GDPRRequest req = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GDPRRequest not found with id " + id));
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        req.setSecus(user);
        req.setRequestType(dto.getRequestType());
        req.setStatus(dto.getStatus());
        req.setCompletedAt(dto.getCompletedAt());
        GDPRRequest updated = repo.save(req);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("GDPRRequest not found with id " + id);
        }
        repo.deleteById(id);
    }

    private GDPRRequestDTO toDto(GDPRRequest req) {
        return GDPRRequestDTO.builder()
                .id(req.getId())
                .userId(req.getSecus().getId())
                .requestType(req.getRequestType())
                .status(req.getStatus())
                .submittedAt(req.getSubmittedAt())
                .completedAt(req.getCompletedAt())
                .build();
    }
}
