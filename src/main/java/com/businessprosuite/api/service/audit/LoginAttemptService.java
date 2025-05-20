package com.businessprosuite.api.service.audit;

import com.businessprosuite.api.dto.audit.LoginAttemptDTO;
import java.util.List;

public interface LoginAttemptService {
    List<LoginAttemptDTO> findAll();
    LoginAttemptDTO findById(Integer id);
    LoginAttemptDTO create(LoginAttemptDTO dto);
    LoginAttemptDTO update(Integer id, LoginAttemptDTO dto);
    void delete(Integer id);
}
