package com.businessprosuite.api.dto.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RegisterRequest DTO Tests")
class RegisterRequestTest {

    @Test
    @DisplayName("Should create RegisterRequest with all fields")
    void createRegisterRequest_WithAllFields_ShouldSetCorrectly() {
        // Given
        String username = "testuser";
        String password = "testpassword";
        String email = "test@example.com";

        // When
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        // Then
        assertThat(registerRequest.getUsername()).isEqualTo(username);
        assertThat(registerRequest.getPassword()).isEqualTo(password);
        assertThat(registerRequest.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should handle null values")
    void createRegisterRequest_WithNullValues_ShouldHandleCorrectly() {
        // When
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(null)
                .password(null)
                .email(null)
                .build();

        // Then
        assertThat(registerRequest.getUsername()).isNull();
        assertThat(registerRequest.getPassword()).isNull();
        assertThat(registerRequest.getEmail()).isNull();
    }

    @Test
    @DisplayName("Should support setter methods")
    void registerRequest_SetterMethods_ShouldWorkCorrectly() {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();

        // When
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("newpassword");
        registerRequest.setEmail("new@example.com");

        // Then
        assertThat(registerRequest.getUsername()).isEqualTo("newuser");
        assertThat(registerRequest.getPassword()).isEqualTo("newpassword");
        assertThat(registerRequest.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void registerRequest_WithEmptyStrings_ShouldHandleCorrectly() {
        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("")
                .password("")
                .email("")
                .build();

        // Then
        assertThat(registerRequest.getUsername()).isEmpty();
        assertThat(registerRequest.getPassword()).isEmpty();
        assertThat(registerRequest.getEmail()).isEmpty();
    }

    @Test
    @DisplayName("Should support equals and hashCode")
    void registerRequest_EqualsAndHashCode_ShouldWorkCorrectly() {
        // Given
        RegisterRequest request1 = RegisterRequest.builder()
                .username("user1")
                .password("pass1")
                .email("user1@example.com")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .username("user1")
                .password("pass1")
                .email("user1@example.com")
                .build();

        RegisterRequest request3 = RegisterRequest.builder()
                .username("user2")
                .password("pass2")
                .email("user2@example.com")
                .build();

        // Then
        assertThat(request1).isEqualTo(request2);
        assertThat(request1).isNotEqualTo(request3);
        assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        assertThat(request1.hashCode()).isNotEqualTo(request3.hashCode());
    }

    @Test
    @DisplayName("Should support toString method")
    void registerRequest_ToString_ShouldWorkCorrectly() {
        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("testuser")
                .password("testpassword")
                .email("test@example.com")
                .build();

        // When
        String toString = registerRequest.toString();

        // Then
        assertThat(toString).contains("testuser");
        assertThat(toString).contains("test@example.com");
        assertThat(toString).contains("RegisterRequest");
    }

    @Test
    @DisplayName("Should handle special characters in email")
    void registerRequest_WithSpecialCharactersInEmail_ShouldHandleCorrectly() {
        // Given
        String emailWithSpecialChars = "test+tag@example-domain.co.uk";

        // When
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("testuser")
                .password("password")
                .email(emailWithSpecialChars)
                .build();

        // Then
        assertThat(registerRequest.getEmail()).isEqualTo(emailWithSpecialChars);
    }

    @Test
    @DisplayName("Should handle whitespace in fields")
    void registerRequest_WithWhitespace_ShouldPreserveWhitespace() {
        // Given
        String usernameWithSpaces = "  test user  ";
        String passwordWithSpaces = "  test pass  ";
        String emailWithSpaces = "  test@example.com  ";

        // When
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(usernameWithSpaces)
                .password(passwordWithSpaces)
                .email(emailWithSpaces)
                .build();

        // Then
        assertThat(registerRequest.getUsername()).isEqualTo(usernameWithSpaces);
        assertThat(registerRequest.getPassword()).isEqualTo(passwordWithSpaces);
        assertThat(registerRequest.getEmail()).isEqualTo(emailWithSpaces);
    }
} 