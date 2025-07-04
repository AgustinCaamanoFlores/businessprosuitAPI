package com.businessprosuite.api.security;

import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final SecurityUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof OAuth2AuthenticationToken oauth2)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid OAuth2 authentication");
            return;
        }
        Map<String, Object> attrs = oauth2.getPrincipal().getAttributes();
        String email = (String) attrs.get("email");
        String name = (String) attrs.getOrDefault("name", email);
        SecurityUser user = userRepo.findBySecusEmail(email)
                .orElseGet(() -> {
                    SecurityUser u = new SecurityUser();
                    u.setSecusName(name);
                    u.setSecusEmail(email);
                    u.setSecusPassword(passwordEncoder.encode("oauth2-user"));
                    u.setSecusAvailable((byte) 1);
                    u.setSecusActive((byte) 1);
                    return userRepo.save(u);
                });

        String token = jwtUtil.generateToken(org.springframework.security.core.userdetails.User
                .withUsername(user.getSecusName())
                .password(user.getSecusPassword())
                .authorities("ROLE_USER")
                .build());

        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
    }
}
