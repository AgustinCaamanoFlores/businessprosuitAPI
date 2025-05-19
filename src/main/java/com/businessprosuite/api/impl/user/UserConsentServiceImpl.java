package com.businessprosuite.api.service.user.impl;

import com.businessprosuite.api.dto.user.UserConsentDTO;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.model.user.UserConsent;
import com.businessprosuite.api.repository.user.UserConsentRepository;
import com.businessprosuite.api.repository.user.UserRepository;
import com.businessprosuite.api.service.user.UserConsentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserConsentServiceImpl implements UserConsentService {

    private final UserConsentRepository consentRepo;
    private final UserRepository         userRepo;

    @Override
    public List<UserConsentDTO> findAll() {
        return consentRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserConsentDTO findById(Integer id) {
        UserConsent consent = consentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserConsent no encontrado con id " + id));
        return toDTO(consent);
    }

    @Override
    public UserConsentDTO create(UserConsentDTO dto) {
        UserConsent consent = toEntity(dto, new UserConsent());
        UserConsent saved    = consentRepo.save(consent);
        return toDTO(saved);
    }

    @Override
    public UserConsentDTO update(Integer id, UserConsentDTO dto) {
        UserConsent consent = consentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserConsent no encontrado con id " + id));
        toEntity(dto, consent);
        UserConsent updated = consentRepo.save(consent);
        return toDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!consentRepo.existsById(id)) {
            throw new EntityNotFoundException("UserConsent no encontrado con id " + id);
        }
        consentRepo.deleteById(id);
    }

    // ——— Métodos de mapeo entidad ↔ DTO ———

    private UserConsentDTO toDTO(UserConsent c) {
        return UserConsentDTO.builder()
                .id(c.getId())
                .userId(c.getUsrConsentUsr().getId())
                .type(c.getUsrConsentType())
                .grantedAt(c.getUsrConsentGrantedAt())
                .revokedAt(c.getUsrConsentRevokedAt())
                .build();
    }

    private UserConsent toEntity(UserConsentDTO dto, UserConsent c) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User no encontrado con id " + dto.getUserId()));
        c.setUsrConsentUsr(user);
        c.setUsrConsentType(dto.getType());
        c.setUsrConsentGrantedAt(dto.getGrantedAt());
        c.setUsrConsentRevokedAt(dto.getRevokedAt());
        return c;
    }
}
