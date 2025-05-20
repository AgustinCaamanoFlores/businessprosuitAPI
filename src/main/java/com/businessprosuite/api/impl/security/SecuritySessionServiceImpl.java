package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecuritySessionDTO;
import com.businessprosuite.api.model.security.SecuritySession;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecuritySessionRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.security.SecuritySessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecuritySessionServiceImpl implements SecuritySessionService {

    private final SecuritySessionRepository sessionRepo;
    private final SecurityUserRepository userRepo;

    @Override
    public List<SecuritySessionDTO> findAll() {
        return sessionRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecuritySessionDTO findById(String sessId) {
        SecuritySession sess = sessionRepo.findById(sessId)
                .orElseThrow(() -> new EntityNotFoundException("SecuritySession not found with id " + sessId));
        return toDto(sess);
    }

    @Override
    public SecuritySessionDTO create(SecuritySessionDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        SecuritySession sess = new SecuritySession();
        sess.setSessId(dto.getSessId());
        sess.setSecus(user);
        sess.setToken(dto.getToken());
        sess.setIssuedAt(dto.getIssuedAt());
        sess.setExpiresAt(dto.getExpiresAt());
        sess.setIpAddress(dto.getIpAddress());
        sess.setUserAgent(dto.getUserAgent());

        SecuritySession saved = sessionRepo.save(sess);
        return toDto(saved);
    }

    @Override
    public SecuritySessionDTO update(String sessId, SecuritySessionDTO dto) {
        SecuritySession sess = sessionRepo.findById(sessId)
                .orElseThrow(() -> new EntityNotFoundException("SecuritySession not found with id " + sessId));
        SecurityUser user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        sess.setSecus(user);
        sess.setToken(dto.getToken());
        sess.setIssuedAt(dto.getIssuedAt());
        sess.setExpiresAt(dto.getExpiresAt());
        sess.setIpAddress(dto.getIpAddress());
        sess.setUserAgent(dto.getUserAgent());

        SecuritySession updated = sessionRepo.save(sess);
        return toDto(updated);
    }

    @Override
    public void delete(String sessId) {
        if (!sessionRepo.existsById(sessId)) {
            throw new EntityNotFoundException("SecuritySession not found with id " + sessId);
        }
        sessionRepo.deleteById(sessId);
    }

    private SecuritySessionDTO toDto(SecuritySession sess) {
        return SecuritySessionDTO.builder()
                .sessId(sess.getSessId())
                .userId(sess.getSecus().getId())
                .token(sess.getToken())
                .issuedAt(sess.getIssuedAt())
                .expiresAt(sess.getExpiresAt())
                .ipAddress(sess.getIpAddress())
                .userAgent(sess.getUserAgent())
                .build();
    }
}
