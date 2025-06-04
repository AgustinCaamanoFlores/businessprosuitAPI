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
import org.mockito.junit.jupiter.MockitoExtension;

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
}
