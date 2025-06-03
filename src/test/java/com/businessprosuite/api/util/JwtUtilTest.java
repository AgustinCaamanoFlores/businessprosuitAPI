package com.businessprosuite.api.util;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JwtUtil Tests")
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails testUserDetails;
    private final String testSecret = "test-secret-key-for-jwt-tokens-that-is-long-enough-for-HS512";
    private final Long testExpiration = 86400000L; // 24 hours

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", testExpiration);
        
        // Create real UserDetails for tests
        testUserDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should generate valid JWT token")
    void generateToken_WithValidUserDetails_ShouldReturnValidToken() {
        // When
        String token = jwtUtil.generateToken(testUserDetails);

        // Then
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3); // JWT has 3 parts
    }

    @Test
    @DisplayName("Should extract username from valid token")
    void extractUsername_WithValidToken_ShouldReturnCorrectUsername() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);

        // When
        String extractedUsername = jwtUtil.extractUsername(token);

        // Then
        assertThat(extractedUsername).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should extract expiration date from token")
    void extractExpiration_WithValidToken_ShouldReturnFutureDate() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);

        // When
        Date expiration = jwtUtil.extractExpiration(token);

        // Then
        assertThat(expiration).isAfter(new Date());
        assertThat(expiration.getTime() - System.currentTimeMillis())
                .isCloseTo(testExpiration, within(5000L)); // Allow 5 second tolerance
    }

    @Test
    @DisplayName("Should validate token successfully for correct user")
    void validateToken_WithCorrectUserAndValidToken_ShouldReturnTrue() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);

        // When
        Boolean isValid = jwtUtil.validateToken(token, testUserDetails);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should invalidate token for incorrect user")
    void validateToken_WithIncorrectUser_ShouldReturnFalse() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);
        UserDetails differentUser = User.builder()
                .username("otheruser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        Boolean isValid = jwtUtil.validateToken(token, differentUser);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should handle malformed token")
    void extractUsername_WithMalformedToken_ShouldThrowException() {
        // Given
        String malformedToken = "invalid.token.format";

        // When & Then
        assertThatThrownBy(() -> jwtUtil.extractUsername(malformedToken))
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    @DisplayName("Should handle empty token")
    void extractUsername_WithEmptyToken_ShouldThrowException() {
        // Given
        String emptyToken = "";

        // When & Then
        assertThatThrownBy(() -> jwtUtil.extractUsername(emptyToken))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should handle null token")
    void extractUsername_WithNullToken_ShouldThrowException() {
        // Given
        String nullToken = null;

        // When & Then
        assertThatThrownBy(() -> jwtUtil.extractUsername(nullToken))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should generate different tokens for different users")
    void generateToken_WithDifferentUsers_ShouldGenerateDifferentTokens() {
        // Given
        UserDetails user1 = User.builder()
                .username("user1")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
        
        UserDetails user2 = User.builder()
                .username("user2")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // When
        String token1 = jwtUtil.generateToken(user1);
        String token2 = jwtUtil.generateToken(user2);

        // Then
        assertThat(token1).isNotEqualTo(token2);
        assertThat(jwtUtil.extractUsername(token1)).isEqualTo("user1");
        assertThat(jwtUtil.extractUsername(token2)).isEqualTo("user2");
    }

    @Test
    @DisplayName("Should generate different tokens for same user at different times")
    void generateToken_ForSameUserAtDifferentTimes_ShouldGenerateDifferentTokens() throws InterruptedException {
        // When
        String token1 = jwtUtil.generateToken(testUserDetails);
        Thread.sleep(1000); // Wait 1 second to ensure different timestamp
        String token2 = jwtUtil.generateToken(testUserDetails);

        // Then
        assertThat(token1).isNotEqualTo(token2);
        assertThat(jwtUtil.extractUsername(token1)).isEqualTo("testuser");
        assertThat(jwtUtil.extractUsername(token2)).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should validate token without UserDetails")
    void validateToken_WithoutUserDetails_ShouldWork() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);

        // When
        Boolean isValid = jwtUtil.validateToken(token);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should handle expired token validation")
    void validateToken_WithExpiredToken_ShouldReturnFalse() throws InterruptedException {
        // Given
        JwtUtil shortExpirationUtil = new JwtUtil();
        ReflectionTestUtils.setField(shortExpirationUtil, "secret", testSecret);
        ReflectionTestUtils.setField(shortExpirationUtil, "expiration", 1L); // 1ms
        
        String token = shortExpirationUtil.generateToken(testUserDetails);
        Thread.sleep(10); // Wait for expiration

        // When
        Boolean isValid = shortExpirationUtil.validateToken(token, testUserDetails);

        // Then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should handle null UserDetails in validation")
    void validateToken_WithNullUserDetails_ShouldReturnFalse() {
        // Given
        String token = jwtUtil.generateToken(testUserDetails);

        // When
        Boolean isValid = jwtUtil.validateToken(token, null);

        // Then
        assertThat(isValid).isFalse();
    }
} 