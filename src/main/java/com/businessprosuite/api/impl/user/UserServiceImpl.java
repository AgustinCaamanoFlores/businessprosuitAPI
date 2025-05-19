package com.businessprosuite.api.impl.user;

import com.businessprosuite.api.dto.user.UserDTO;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.repository.user.UserRepository;
import com.businessprosuite.api.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    public UserServiceImpl(UserRepository repo) { this.repo = repo; }

    @Override
    public List<UserDTO> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        return toDto(u);
    }

    @Override
    public UserDTO create(UserDTO dto) {
        User u = toEntity(dto);
        return toDto(repo.save(u));
    }

    @Override
    public UserDTO update(Integer id, UserDTO dto) {
        User u = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        u.setUsrFirstName(dto.getFirstName());
        u.setUsrLastName(dto.getLastName());
        u.setUsrEmail(dto.getEmail());
        u.setUsrPhone(dto.getPhone());
        // …otros setters según DTO
        return toDto(repo.save(u));
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private UserDTO toDto(User u) {
        return UserDTO.builder()
                .id(u.getId())
                .firstName(u.getUsrFirstName())
                .lastName(u.getUsrLastName())
                .email(u.getUsrEmail())
                .phone(u.getUsrPhone())
                .residenceCountryId(
                        u.getUsrResidence() !=null ? Integer.valueOf(u.getUsrResidence().getCodigo()) : null)
                .consent(u.getUsrConsent())
                .consentDate(u.getUsrConsentDate())
                .address(u.getUsrAddress())
                .idNumber(u.getUsrIdNumber())
                .createdAt(u.getUsrCreatedAt())
                .updatedAt(u.getUsrUpdatedAt())
                .build();
    }

    private User toEntity(UserDTO d) {
        User u = new User();
        u.setUsrFirstName(d.getFirstName());
        u.setUsrLastName(d.getLastName());
        u.setUsrEmail(d.getEmail());
        u.setUsrPhone(d.getPhone());
        // Asignar relaciones (Company, Country) según tu lógica…
        return u;
    }
}
