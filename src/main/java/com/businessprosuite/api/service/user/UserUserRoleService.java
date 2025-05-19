package com.businessprosuite.api.service.user;

import com.businessprosuite.api.dto.user.UserUserRoleDTO;
import java.util.List;

public interface UserUserRoleService {
    List<UserUserRoleDTO> findAll();
    UserUserRoleDTO findById(Integer userId, Integer roleId);
    UserUserRoleDTO create(UserUserRoleDTO dto);
    void delete(Integer userId, Integer roleId);
}
