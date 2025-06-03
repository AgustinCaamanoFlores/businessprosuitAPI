package com.businessprosuite.api.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "app.security.cors.allowed-origins=http://localhost:3000"
})
class SecurityConfigTest {

    @Mock
    private AuthenticationConfiguration authConfig;

    @Test
    void securityConfig_ShouldBeAnnotatedCorrectly() {
        // Given & When
        SecurityConfig config = new SecurityConfig();
        
        // Then
        assertThat(config.getClass().isAnnotationPresent(Configuration.class)).isTrue();
        assertThat(config.getClass().isAnnotationPresent(EnableWebSecurity.class)).isTrue();
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        // Given
        SecurityConfig config = new SecurityConfig();
        
        // When & Then - Should not throw exception when creating authentication manager
        assertThatCode(() -> config.authenticationManager(authConfig))
            .doesNotThrowAnyException();
    }
} 