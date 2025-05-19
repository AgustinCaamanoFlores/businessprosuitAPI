package com.businessprosuite.api.service.user;

import com.businessprosuite.api.dto.user.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Integer id);
    UserDTO create(UserDTO dto);
    UserDTO update(Integer id, UserDTO dto);
    void delete(Integer id);
}
