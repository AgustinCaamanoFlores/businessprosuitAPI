package com.businessprosuite.api.service.user;

import com.businessprosuite.api.dto.user.UserRoleDTO;
import java.util.List;

public interface UserRoleService {
    List<UserRoleDTO> findAll();
    UserRoleDTO findById(Integer id);
    UserRoleDTO create(UserRoleDTO dto);
    UserRoleDTO update(Integer id, UserRoleDTO dto);
    void delete(Integer id);
}
