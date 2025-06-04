package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.core.TranslationDTO;
import com.businessprosuite.api.impl.core.TranslationServiceImpl;
import com.businessprosuite.api.model.core.Translation;
import com.businessprosuite.api.repository.core.TranslationRepository;
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
class TranslationServiceTest {
    @Mock
    private TranslationRepository repo;

    @InjectMocks
    private TranslationServiceImpl service;

    @Test
    void create_success() {
        TranslationDTO dto = TranslationDTO.builder()
                .lang("es")
                .key("hello")
                .value("hola")
                .build();

        Translation saved = new Translation();
        saved.setId(1);
        saved.setIntLang("es");
        saved.setIntKey("hello");
        saved.setIntValue("hola");
        saved.setIntCreatedAt(LocalDateTime.now());
        saved.setIntUpdatedAt(LocalDateTime.now());

        when(repo.save(any(Translation.class))).thenReturn(saved);

        TranslationDTO result = service.create(dto);

        assertEquals(1, result.getId());
        verify(repo).save(any(Translation.class));
    }

    @Test
    void findById_notFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }
}
