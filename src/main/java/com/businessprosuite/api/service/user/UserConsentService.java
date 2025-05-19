package com.businessprosuite.api.service.user;

import com.businessprosuite.api.dto.user.UserConsentDTO;
import java.util.List;

public interface UserConsentService {
    List<UserConsentDTO> findAll();
    UserConsentDTO findById(Integer id);
    UserConsentDTO create(UserConsentDTO dto);
    UserConsentDTO update(Integer id, UserConsentDTO dto);
    void delete(Integer id);
}
