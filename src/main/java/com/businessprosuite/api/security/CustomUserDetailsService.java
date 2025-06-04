package com.businessprosuite.api.security;

import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = userRepo.findBySecusName(username)
                .orElseGet(() -> userRepo.findBySecusEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
        return User.builder()
                .username(user.getSecusName())
                .password(user.getSecusPassword())
                .disabled(user.getSecusActive() == 0)
                .authorities(Collections.emptyList())
                .build();
    }
}
