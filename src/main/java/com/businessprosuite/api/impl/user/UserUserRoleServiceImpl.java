package com.businessprosuite.api.impl.user;

import com.businessprosuite.api.dto.user.UserUserRoleDTO;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.model.user.Role;
import com.businessprosuite.api.model.user.UserRole;
import com.businessprosuite.api.model.user.UserRoleId;
import com.businessprosuite.api.repository.user.UserRepository;
import com.businessprosuite.api.repository.user.UserUserRoleRepository;
import com.businessprosuite.api.repository.user.UserRoleRepository;
import com.businessprosuite.api.service.user.UserUserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserUserRoleServiceImpl implements UserUserRoleService {

    private final UserUserRoleRepository userRoleRepo;
    private final UserRepository userRepo;
    private final UserRoleRepository roleRepo;

    @Override
    public List<UserUserRoleDTO> findAll() {
        return userRoleRepo.findAll()
                .stream()
                .map(ur -> new UserUserRoleDTO(
                        ur.getId().getUsrUserRoleUsrId(),
                        ur.getId().getUsrUserRoleRoleId()))
                .collect(Collectors.toList());
    }

    @Override
    public UserUserRoleDTO findById(Integer userId, Integer roleId) {
        UserRoleId pk = new UserRoleId(userId, roleId);
        userRoleRepo.findById(pk)
                .orElseThrow(() -> new EntityNotFoundException(
                        "UserRole no encontrado para userId=" + userId + ", roleId=" + roleId));
        return new UserUserRoleDTO(userId, roleId);
    }

    @Override
    public UserUserRoleDTO create(UserUserRoleDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User no encontrado con id " + dto.getUserId()));
        Role role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Role no encontrado con id " + dto.getRoleId()));

        UserRoleId pk = new UserRoleId(dto.getUserId(), dto.getRoleId());
        UserRole ur = UserRole.builder()
                .id(pk)
                .usrUserRole(user)
                .usrRole(role)
                .build();

        userRoleRepo.save(ur);
        return dto;
    }

    @Override
    public void delete(Integer userId, Integer roleId) {
        UserRoleId pk = new UserRoleId(userId, roleId);
        if (!userRoleRepo.existsById(pk)) {
            throw new EntityNotFoundException(
                    "UserRole no encontrado para userId=" + userId + ", roleId=" + roleId);
        }
        userRoleRepo.deleteById(pk);
    }
}