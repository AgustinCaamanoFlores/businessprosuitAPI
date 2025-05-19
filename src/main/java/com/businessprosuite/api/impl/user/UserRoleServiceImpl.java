package com.businessprosuite.api.impl.user;

import com.businessprosuite.api.dto.user.UserRoleDTO;
import com.businessprosuite.api.repository.user.UserRoleRepository;
import com.businessprosuite.api.model.user.Role;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.repository.user.UserRepository;
import com.businessprosuite.api.service.user.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserRoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleDTO findById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        return toDTO(role);
    }

    @Override
    public UserRoleDTO create(UserRoleDTO dto) {
        Role role = toEntity(dto, new Role());
        Role saved = roleRepository.save(role);
        return toDTO(saved);
    }

    @Override
    public UserRoleDTO update(Integer id, UserRoleDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        toEntity(dto, role);
        Role updated = roleRepository.save(role);
        return toDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id " + id);
        }
        roleRepository.deleteById(id);
    }

    // ——— Mapeos entidad ↔ DTO ———
    private UserRoleDTO toDTO(Role role) {
        Set<Integer> ids = role.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return UserRoleDTO.builder()
                .id(role.getId())
                .name(role.getUsrRoleName())
                .userIds(ids)
                .build();
    }

    private Role toEntity(UserRoleDTO dto, Role role) {
        role.setUsrRoleName(dto.getName());

        // Sincronizar la relación ManyToMany
        if (dto.getUserIds() != null) {
            Set<User> users = dto.getUserIds()
                    .stream()
                    .map(uid -> userRepository.findById(uid)
                            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + uid)))
                    .collect(Collectors.toSet());
            role.setUsers(users);
        }
        return role;
    }
}
