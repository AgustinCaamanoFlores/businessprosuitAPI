package com.businessprosuite.api.service.auth;

import com.businessprosuite.api.dto.auth.*;

public interface AuthService {
    
    RegisterResponse register(RegisterRequest request);
    
    AuthResponse login(LoginRequest request);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
} 