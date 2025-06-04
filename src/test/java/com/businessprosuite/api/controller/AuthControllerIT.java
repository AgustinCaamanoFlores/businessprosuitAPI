package com.businessprosuite.api.controller;

import com.businessprosuite.api.config.PasswordConfig;
import com.businessprosuite.api.config.SecurityConfig;
import com.businessprosuite.api.controller.security.AuthController;
import com.businessprosuite.api.controller.user.UserController;
import com.businessprosuite.api.dto.security.AuthRequest;
import com.businessprosuite.api.security.CustomUserDetailsService;
import com.businessprosuite.api.security.JwtUtil;
import com.businessprosuite.api.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {AuthController.class, UserController.class})
@Import({SecurityConfig.class, PasswordConfig.class})
class AuthControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authManager;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;

    @Test
    void login_returnsToken() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authManager.authenticate(any())).thenReturn(authentication);
        UserDetails user = User.withUsername("john").password("pw").authorities(Collections.emptyList()).build();
        when(userDetailsService.loadUserByUsername("john")).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        AuthRequest req = new AuthRequest("john", "pw");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void accessProtectedEndpoint_withValidToken() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authManager.authenticate(any())).thenReturn(authentication);
        UserDetails user = User.withUsername("john").password("pw").authorities(Collections.emptyList()).build();
        when(userDetailsService.loadUserByUsername("john")).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");
        when(jwtUtil.extractUsername("jwt-token")).thenReturn("john");
        when(jwtUtil.validateToken("jwt-token", user)).thenReturn(true);
        when(userService.findAll()).thenReturn(Collections.emptyList());

        AuthRequest req = new AuthRequest("john", "pw");
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
