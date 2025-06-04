package com.businessprosuite.api.controller.auth;

import com.businessprosuite.api.dto.auth.AuthRequestDTO;
import com.businessprosuite.api.dto.auth.AuthResponseDTO;
import com.businessprosuite.api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(AuthResponseDTO.builder().token(token).build());
    }
}
