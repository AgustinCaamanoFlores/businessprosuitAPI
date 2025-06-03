package com.businessprosuite.api.dto.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("LoginRequest DTO Tests")
class AuthRequestTest {

    @Test
    @DisplayName("Should create LoginRequest with all fields")
    void createLoginRequest_WithAllFields_ShouldSetCorrectly() {
        // Given
        String username = "testuser";
        String password = "testpassword";

        // When
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        // Then
        assertThat(loginRequest.getUsername()).isEqualTo(username);
        assertThat(loginRequest.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("Should handle null values")
    void createLoginRequest_WithNullValues_ShouldHandleCorrectly() {
        // When
        LoginRequest loginRequest = LoginRequest.builder()
                .username(null)
                .password(null)
                .build();

        // Then
        assertThat(loginRequest.getUsername()).isNull();
        assertThat(loginRequest.getPassword()).isNull();
    }

    @Test
    @DisplayName("Should support setter methods")
    void loginRequest_SetterMethods_ShouldWorkCorrectly() {
        // Given
        LoginRequest loginRequest = new LoginRequest();

        // When
        loginRequest.setUsername("newuser");
        loginRequest.setPassword("newpassword");

        // Then
        assertThat(loginRequest.getUsername()).isEqualTo("newuser");
        assertThat(loginRequest.getPassword()).isEqualTo("newpassword");
    }

    @Test
    @DisplayName("Should support equals and hashCode")
    void loginRequest_EqualsAndHashCode_ShouldWorkCorrectly() {
        // Given
        LoginRequest request1 = LoginRequest.builder()
                .username("user1")
                .password("pass1")
                .build();

        LoginRequest request2 = LoginRequest.builder()
                .username("user1")
                .password("pass1")
                .build();

        LoginRequest request3 = LoginRequest.builder()
                .username("user2")
                .password("pass2")
                .build();

        // Then
        assertThat(request1).isEqualTo(request2);
        assertThat(request1).isNotEqualTo(request3);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());
    }

    @Test
    @DisplayName("Should support toString method")
    void loginRequest_ToString_ShouldWorkCorrectly() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("testpassword")
                .build();

        // When
        String toString = loginRequest.toString();

        // Then
        assertThat(toString).contains("testuser");
        assertThat(toString).contains("LoginRequest");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void loginRequest_WithEmptyStrings_ShouldHandleCorrectly() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .username("")
                .password("")
                .build();

        // Then
        assertThat(loginRequest.getUsername()).isEmpty();
        assertThat(loginRequest.getPassword()).isEmpty();
    }

    @Test
    @DisplayName("Should handle whitespace in fields")
    void loginRequest_WithWhitespace_ShouldPreserveWhitespace() {
        // Given
        String usernameWithSpaces = "  test user  ";
        String passwordWithSpaces = "  test pass  ";

        // When
        LoginRequest loginRequest = LoginRequest.builder()
                .username(usernameWithSpaces)
                .password(passwordWithSpaces)
                .build();

        // Then
        assertThat(loginRequest.getUsername()).isEqualTo(usernameWithSpaces);
        assertThat(loginRequest.getPassword()).isEqualTo(passwordWithSpaces);
    }
} 