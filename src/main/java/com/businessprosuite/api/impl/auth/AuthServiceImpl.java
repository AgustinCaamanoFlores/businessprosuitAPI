package com.businessprosuite.api.impl.auth;

import com.businessprosuite.api.dto.auth.*;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.config.ConfigCountryRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.auth.AuthService;
import com.businessprosuite.api.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    
    private final SecurityUserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ConfigCompanyRepository configCompanyRepository;
    private final ConfigCountryRepository countryRepository;
    private final SecurityRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.info("Iniciando registro para usuario: {}", request.getUsername());
        
        // Validar que el usuario no exista
        if (existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        if (existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        try {
            // 1. Crear o obtener ConfigCompany
            ConfigCompany configCompany = createOrGetConfigCompany(request);
            
            // 2. Crear la empresa
            Company company = createCompany(request, configCompany);
            
            // 3. Crear el usuario administrador
            SecurityUser user = createAdminUser(request, company);
            
            log.info("Registro exitoso para usuario: {} y empresa: {}", user.getSecusName(), company.getCmpName());
            
            return RegisterResponse.builder()
                    .message("Usuario registrado exitosamente")
                    .user(RegisterResponse.UserInfo.builder()
                            .id(user.getId())
                            .username(user.getSecusName())
                            .email(user.getSecusEmail())
                            .fullName(request.getFullName())
                            .build())
                    .company(RegisterResponse.CompanyInfo.builder()
                            .id(company.getId())
                            .name(company.getCmpName())
                            .email(company.getCmpEmail())
                            .address(company.getCmpAddress())
                            .build())
                    .build();
                    
        } catch (Exception e) {
            log.error("Error durante el registro: {}", e.getMessage(), e);
            throw new RuntimeException("Error durante el registro: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Intento de login para usuario: {}", request.getUsername());
        
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            
            // Generar token JWT
            String token = jwtUtil.generateToken(authentication);
            
            // Obtener información del usuario
            SecurityUser user = userRepository.findBySecusName(request.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            
            // Obtener autoridades
            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            
            // Actualizar último login
            user.setSecusLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            log.info("Login exitoso para usuario: {}", user.getSecusName());
            
            return AuthResponse.builder()
                    .token(token)
                    .username(user.getSecusName())
                    .email(user.getSecusEmail())
                    .fullName(user.getSecusName()) // Puedes agregar un campo fullName al modelo si lo necesitas
                    .authorities(authorities)
                    .expiresIn(86400L) // 24 horas en segundos
                    .build();
                    
        } catch (Exception e) {
            log.error("Error durante el login para usuario {}: {}", request.getUsername(), e.getMessage());
            throw new RuntimeException("Credenciales inválidas");
        }
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findBySecusName(username).isPresent();
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findBySecusEmail(email).isPresent();
    }
    
    private ConfigCompany createOrGetConfigCompany(RegisterRequest request) {
        // Buscar si ya existe una configuración de empresa por defecto
        List<ConfigCompany> existing = configCompanyRepository.findAll();
        
        if (!existing.isEmpty()) {
            return existing.get(0); // Usar la primera configuración existente
        }
        
        // Crear nueva configuración de empresa
        ConfigCompany configCompany = ConfigCompany.builder()
                .empNombre(request.getCompanyName())
                .empMonedaBase(request.getBaseCurrency())
                .empRegion(request.getRegion())
                .empCreadoAt(LocalDateTime.now())
                .build();
                
        return configCompanyRepository.save(configCompany);
    }
    
    private Company createCompany(RegisterRequest request, ConfigCompany configCompany) {
        // Obtener país
        ConfigCountry country = countryRepository.findById(request.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException("País no encontrado: " + request.getCountryCode()));
        
        Company company = Company.builder()
                .configCompany(configCompany)
                .cmpName(request.getCompanyName())
                .cmpAddress(request.getCompanyAddress())
                .cmpPhone(request.getCompanyPhone())
                .cmpEmail(request.getCompanyEmail())
                .cmpTaxId(request.getCompanyTaxId())
                .cmpConfigCountryCodigo(country)
                .cmpCreatedAt(LocalDateTime.now())
                .cmpUpdatedAt(LocalDateTime.now())
                .build();
                
        return companyRepository.save(company);
    }
    
    private SecurityUser createAdminUser(RegisterRequest request, Company company) {
        // Obtener o crear rol ADMIN
        SecurityRole adminRole = roleRepository.findBySecrlName("ADMIN")
                .orElseGet(() -> {
                    SecurityRole newRole = SecurityRole.builder()
                            .secrlName("ADMIN")
                            .secrlDescription("Administrador del sistema")
                            .secrlCreatedAt(LocalDateTime.now())
                            .secrlUpdatedAt(LocalDateTime.now())
                            .build();
                    return roleRepository.save(newRole);
                });
        
        SecurityUser user = SecurityUser.builder()
                .secusName(request.getUsername())
                .secusPassword(passwordEncoder.encode(request.getPassword()))
                .secusEmail(request.getEmail())
                .secusAvailable((byte) 1)
                .secusActive((byte) 1)
                .secusMfaEnabled((byte) 0)
                .secusRole(adminRole)
                .secusCmp(company)
                .secusCreatedAt(LocalDateTime.now())
                .secusUpdatedAt(LocalDateTime.now())
                .build();
                
        return userRepository.save(user);
    }
} 