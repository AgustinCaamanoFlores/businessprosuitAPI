package com.businessprosuite.api.controller.auth;

import com.businessprosuite.api.dto.auth.AuthRequestDTO;
import com.businessprosuite.api.dto.auth.AuthResponseDTO;
import com.businessprosuite.api.security.JwtUtil;
import com.businessprosuite.api.service.security.TotpService;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
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
    private final SecurityUserRepository userRepo;
    private final TotpService totpService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityUser secUser = userRepo.findBySecusName(request.getUsername())
                .orElseGet(() -> userRepo.findBySecusEmail(request.getUsername())
                        .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found")));

        if (secUser.getSecusMfaEnabled() == 1) {
            if (request.getOtp() == null || !totpService.verifyCode(secUser.getSecusMfaSecret(), request.getOtp())) {
                return ResponseEntity.status(401).build();
            }
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(AuthResponseDTO.builder().token(token).build());
    }
}
