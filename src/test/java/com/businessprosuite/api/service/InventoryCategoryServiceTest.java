package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.inventory.InventoryCategoryDTO;
import com.businessprosuite.api.impl.inventory.InventoryCategoryServiceImpl;
import com.businessprosuite.api.model.inventory.InventoryCategory;
import com.businessprosuite.api.repository.inventory.InventoryCategoryRepository;
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
class InventoryCategoryServiceTest {
    @Mock
    private InventoryCategoryRepository repo;

    @InjectMocks
    private InventoryCategoryServiceImpl service;

    @Test
    void findById_success() {
        InventoryCategory entity = new InventoryCategory();
        entity.setId(1);
        entity.setInvCatName("Cat1");
        entity.setInvCatDescription("desc");
        entity.setInvCatCreatedAt(LocalDateTime.now());
        entity.setInvCatUpdatedAt(LocalDateTime.now());
        when(repo.findById(1)).thenReturn(Optional.of(entity));

        InventoryCategoryDTO dto = service.findById(1);

        assertEquals(1, dto.getId());
        verify(repo).findById(1);
    }

    @Test
    void delete_notFound() {
        when(repo.existsById(1)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete(1));
    }
}
