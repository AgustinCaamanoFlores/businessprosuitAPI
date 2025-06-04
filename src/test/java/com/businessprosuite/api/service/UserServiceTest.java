package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.user.UserDTO;
import com.businessprosuite.api.impl.user.UserServiceImpl;
import com.businessprosuite.api.model.user.User;
import com.businessprosuite.api.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void findById_success() {
        User user = new User();
        user.setId(5);
        user.setUsrFirstName("Agustin");
        user.setUsrLastName("Test");
        when(repo.findById(5)).thenReturn(Optional.of(user));

        UserDTO result = service.findById(5);

        assertEquals(5, result.getId());
        verify(repo).findById(5);
    }
}
