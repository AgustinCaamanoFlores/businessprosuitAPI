package com.businessprosuite.api.security;

import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementación personalizada de UserDetailsService para cargar usuarios
 * desde la base de datos y proporcionar información de autenticación.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        
        SecurityUser securityUser = userRepository.findBySecusName(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        // Verificar si el usuario está activo
        if (securityUser.getSecusActive() == 0) {
            log.warn("User account is disabled: {}", username);
            throw new UsernameNotFoundException("La cuenta del usuario está deshabilitada: " + username);
        }

        // Verificar si el usuario está disponible
        if (securityUser.getSecusAvailable() == 0) {
            log.warn("User account is not available: {}", username);
            throw new UsernameNotFoundException("La cuenta del usuario no está disponible: " + username);
        }

        Collection<GrantedAuthority> authorities = getAuthorities(securityUser);

        return User.builder()
                .username(securityUser.getSecusName())
                .password(securityUser.getSecusPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(securityUser.getSecusActive() == 0)
                .build();
    }

    /**
     * Obtiene las autoridades/roles del usuario
     */
    private Collection<GrantedAuthority> getAuthorities(SecurityUser securityUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Agregar rol del usuario
        if (securityUser.getSecusRole() != null) {
            String roleName = securityUser.getSecusRole().getSecrlName();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
            log.debug("Added role: ROLE_{}", roleName.toUpperCase());
        }

        // Aquí podrías agregar lógica adicional para permisos específicos
        // basados en roles, grupos, etc.

        return authorities;
    }

    /**
     * Método auxiliar para buscar usuario por email
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "'email:' + #email")
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);
        
        SecurityUser securityUser = userRepository.findBySecusEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new UsernameNotFoundException("Usuario no encontrado con email: " + email);
                });

        return loadUserByUsername(securityUser.getSecusName());
    }
} 