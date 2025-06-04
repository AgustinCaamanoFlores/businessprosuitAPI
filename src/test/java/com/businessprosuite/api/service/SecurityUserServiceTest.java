package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.security.SecurityUserDTO;
import com.businessprosuite.api.impl.security.SecurityUserServiceImpl;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.security.SecurityRole;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.security.SecurityRoleRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityUserServiceTest {
    @Mock
    private SecurityUserRepository userRepo;
    @Mock
    private SecurityRoleRepository roleRepo;
    @Mock
    private CompanyRepository companyRepo;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SecurityUserServiceImpl service;

    @Test
    void findById_success() {
        SecurityRole role = new SecurityRole();
        role.setId(2);
        Company cmp = new Company();
        cmp.setId(3);
        SecurityUser user = new SecurityUser();
        user.setId(1);
        user.setSecusName("user1");
        user.setSecusRole(role);
        user.setSecusCmp(cmp);
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        SecurityUserDTO dto = service.findById(1);

        assertEquals(1, dto.getId());
        verify(userRepo).findById(1);
    }

    @Test
    void delete_notFound() {
        when(userRepo.existsById(9)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete(9));
    }

    @Test
    void create_encodesPassword() {
        SecurityUserDTO dto = SecurityUserDTO.builder()
                .name("user")
                .password("plain")
                .roleId(1)
                .companyId(2)
                .build();

        SecurityRole role = new SecurityRole();
        role.setId(1);
        Company company = new Company();
        company.setId(2);

        when(roleRepo.findById(1)).thenReturn(Optional.of(role));
        when(companyRepo.findById(2)).thenReturn(Optional.of(company));
        when(passwordEncoder.encode("plain")).thenReturn("encoded");

        SecurityUser saved = new SecurityUser();
        saved.setId(10);
        saved.setSecusName("user");
        saved.setSecusPassword("encoded");
        saved.setSecusRole(role);
        saved.setSecusCmp(company);
        saved.setSecusCreatedAt(LocalDateTime.now());
        saved.setSecusUpdatedAt(LocalDateTime.now());

        ArgumentCaptor<SecurityUser> captor = ArgumentCaptor.forClass(SecurityUser.class);
        when(userRepo.save(any(SecurityUser.class))).thenReturn(saved);

        SecurityUserDTO result = service.create(dto);

        verify(passwordEncoder).encode("plain");
        verify(userRepo).save(captor.capture());
        assertEquals("encoded", captor.getValue().getSecusPassword());
        assertEquals("encoded", result.getPassword());
    }

    @Test
    void update_encodesPassword() {
        SecurityUserDTO dto = SecurityUserDTO.builder()
                .name("new")
                .password("plain2")
                .roleId(1)
                .companyId(2)
                .build();

        SecurityRole role = new SecurityRole();
        role.setId(1);
        Company company = new Company();
        company.setId(2);

        SecurityUser existing = new SecurityUser();
        existing.setId(5);
        existing.setSecusRole(role);
        existing.setSecusCmp(company);

        when(userRepo.findById(5)).thenReturn(Optional.of(existing));
        when(roleRepo.findById(1)).thenReturn(Optional.of(role));
        when(companyRepo.findById(2)).thenReturn(Optional.of(company));
        when(passwordEncoder.encode("plain2")).thenReturn("encoded2");

        ArgumentCaptor<SecurityUser> captor = ArgumentCaptor.forClass(SecurityUser.class);
        when(userRepo.save(any(SecurityUser.class))).thenReturn(existing);

        SecurityUserDTO result = service.update(5, dto);

        verify(passwordEncoder).encode("plain2");
        verify(userRepo).save(captor.capture());
        assertEquals("encoded2", captor.getValue().getSecusPassword());
        assertEquals("encoded2", result.getPassword());
    }
}
