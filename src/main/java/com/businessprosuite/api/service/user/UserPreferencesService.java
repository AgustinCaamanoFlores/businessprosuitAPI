package com.businessprosuite.api.service.user;

import com.businessprosuite.api.dto.user.UserPreferencesDTO;
import java.util.List;

public interface UserPreferencesService {
    List<UserPreferencesDTO> findAll();
    UserPreferencesDTO findById(Integer id);
    UserPreferencesDTO create(UserPreferencesDTO dto);
    UserPreferencesDTO update(Integer id, UserPreferencesDTO dto);
    void delete(Integer id);
}
