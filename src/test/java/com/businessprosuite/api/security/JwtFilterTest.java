package com.businessprosuite.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtFilterTest {

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_validToken_setsAuthentication() throws Exception {
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UserDetailsService uds = mock(UserDetailsService.class);
        JwtFilter filter = new JwtFilter(jwtUtil, uds);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token123");
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        UserDetails user = User.withUsername("john").password("pw").authorities(Collections.emptyList()).build();
        when(jwtUtil.extractUsername("token123")).thenReturn("john");
        when(uds.loadUserByUsername("john")).thenReturn(user);
        when(jwtUtil.validateToken("token123", user)).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_invalidToken_noAuthentication() throws Exception {
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UserDetailsService uds = mock(UserDetailsService.class);
        JwtFilter filter = new JwtFilter(jwtUtil, uds);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer bad");
        HttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        when(jwtUtil.extractUsername("bad")).thenThrow(new RuntimeException());

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }
}
