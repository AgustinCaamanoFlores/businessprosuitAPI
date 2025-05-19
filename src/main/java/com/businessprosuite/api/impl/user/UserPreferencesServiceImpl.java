package com.businessprosuite.api.service.user.impl;

import com.businessprosuite.api.dto.user.UserPreferencesDTO;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.model.user.UserPreferences;
import com.businessprosuite.api.repository.user.UserPreferencesRepository;
import com.businessprosuite.api.repository.user.UserRepository;
import com.businessprosuite.api.service.user.UserPreferencesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPreferencesServiceImpl implements UserPreferencesService {

    private final UserPreferencesRepository prefsRepo;
    private final UserRepository           userRepo;

    @Override
    public List<UserPreferencesDTO> findAll() {
        return prefsRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserPreferencesDTO findById(Integer id) {
        UserPreferences prefs = prefsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "UserPreferences no encontrado con id " + id));
        return toDTO(prefs);
    }

    @Override
    public UserPreferencesDTO create(UserPreferencesDTO dto) {
        UserPreferences prefs = toEntity(dto, new UserPreferences());
        UserPreferences saved  = prefsRepo.save(prefs);
        return toDTO(saved);
    }

    @Override
    public UserPreferencesDTO update(Integer id, UserPreferencesDTO dto) {
        UserPreferences prefs = prefsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "UserPreferences no encontrado con id " + id));
        toEntity(dto, prefs);
        UserPreferences updated = prefsRepo.save(prefs);
        return toDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!prefsRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    "UserPreferences no encontrado con id " + id);
        }
        prefsRepo.deleteById(id);
    }

    // ——— Métodos de mapeo entidad ↔ DTO ———

    private UserPreferencesDTO toDTO(UserPreferences p) {
        return UserPreferencesDTO.builder()
                .id(p.getId())
                .userId(p.getUsrPrefUsr().getId())
                .key(p.getUsrPrefKey())
                .value(p.getUsrPrefValue())
                .createdAt(p.getUsrPrefCreatedAt())
                .updatedAt(p.getUsrPrefUpdatedAt())
                .build();
    }

    private UserPreferences toEntity(UserPreferencesDTO dto, UserPreferences p) {
        User usr = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User no encontrado con id " + dto.getUserId()));
        p.setUsrPrefUsr(usr);
        p.setUsrPrefKey(dto.getKey());
        p.setUsrPrefValue(dto.getValue());
        // Las fechas createdAt y updatedAt se manejan por defecto en BD,
        // pero si vienen en el DTO, las aplicamos:
        p.setUsrPrefCreatedAt(dto.getCreatedAt());
        p.setUsrPrefUpdatedAt(dto.getUpdatedAt());
        return p;
    }
}
