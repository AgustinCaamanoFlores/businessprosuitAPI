package com.businessprosuite.api.config;

import com.businessprosuite.api.config.ApiErrorResponse;
import com.businessprosuite.api.config.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private MethodArgumentNotValidException validationException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException")
    void handleValidationExceptions_ShouldReturnBadRequest() {
        // Given
        FieldError fieldError = new FieldError("testObject", "testField", "Test error message");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleValidationExceptions(validationException, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Validation Failed");
        assertThat(response.getBody().getValidationErrors()).containsKey("testField");
        assertThat(response.getBody().getValidationErrors().get("testField")).isEqualTo("Test error message");
    }

    @Test
    @DisplayName("Should handle ConstraintViolationException")
    void handleConstraintViolationException_ShouldReturnBadRequest() {
        // Given
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(jakarta.validation.Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("testProperty");
        when(violation.getMessage()).thenReturn("Constraint violation message");
        
        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleConstraintViolationException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Constraint Violation");
        assertThat(response.getBody().getValidationErrors()).containsKey("testProperty");
    }

    @Test
    @DisplayName("Should handle EntityNotFoundException")
    void handleEntityNotFoundException_ShouldReturnNotFound() {
        // Given
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleEntityNotFoundException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getError()).isEqualTo("Entity Not Found");
        assertThat(response.getBody().getMessage()).isEqualTo("Entity not found");
    }

    @Test
    @DisplayName("Should handle BadCredentialsException")
    void handleBadCredentialsException_ShouldReturnUnauthorized() {
        // Given
        BadCredentialsException exception = new BadCredentialsException("Invalid credentials");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleBadCredentialsException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(401);
        assertThat(response.getBody().getError()).isEqualTo("Authentication Failed");
        assertThat(response.getBody().getMessage()).isEqualTo("Credenciales inválidas");
    }

    @Test
    @DisplayName("Should handle AccessDeniedException")
    void handleAccessDeniedException_ShouldReturnForbidden() {
        // Given
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleAccessDeniedException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(403);
        assertThat(response.getBody().getError()).isEqualTo("Access Denied");
        assertThat(response.getBody().getMessage()).isEqualTo("No tiene permisos para acceder a este recurso");
    }

    @Test
    @DisplayName("Should handle MethodArgumentTypeMismatchException")
    void handleMethodArgumentTypeMismatchException_ShouldReturnBadRequest() {
        // Given
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("testParam");
        doReturn(String.class).when(exception).getRequiredType();

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleMethodArgumentTypeMismatchException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Invalid Parameter Type");
        assertThat(response.getBody().getMessage()).contains("testParam");
        assertThat(response.getBody().getMessage()).contains("String");
    }

    @Test
    @DisplayName("Should handle HttpMessageNotReadableException")
    void handleHttpMessageNotReadableException_ShouldReturnBadRequest() {
        // Given
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getMessage()).thenReturn("JSON parse error");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleHttpMessageNotReadableException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(400);
        assertThat(response.getBody().getError()).isEqualTo("Malformed JSON");
        assertThat(response.getBody().getMessage()).isEqualTo("El formato JSON enviado es inválido");
    }

    @Test
    @DisplayName("Should handle generic Exception")
    void handleGlobalException_ShouldReturnInternalServerError() {
        // Given
        Exception exception = new Exception("Unexpected error");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getError()).isEqualTo("Internal Server Error");
        assertThat(response.getBody().getMessage()).isEqualTo("Ha ocurrido un error interno en el servidor");
    }

    @Test
    @DisplayName("Should clean URI description properly")
    void shouldCleanUriDescriptionProperly() {
        // Given
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test/endpoint");
        Exception exception = new Exception("Test error");

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        // Then
        assertThat(response.getBody().getPath()).isEqualTo("/api/test/endpoint");
    }

    @Test
    @DisplayName("Should handle multiple field errors")
    void handleValidationExceptions_WithMultipleErrors_ShouldReturnAllErrors() {
        // Given
        FieldError error1 = new FieldError("testObject", "field1", "Error 1");
        FieldError error2 = new FieldError("testObject", "field2", "Error 2");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        // When
        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleValidationExceptions(validationException, webRequest);

        // Then
        assertThat(response.getBody().getValidationErrors()).hasSize(2);
        assertThat(response.getBody().getValidationErrors()).containsKeys("field1", "field2");
        assertThat(response.getBody().getValidationErrors().get("field1")).isEqualTo("Error 1");
        assertThat(response.getBody().getValidationErrors().get("field2")).isEqualTo("Error 2");
    }
} 