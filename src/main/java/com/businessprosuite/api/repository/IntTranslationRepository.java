package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventory.InventoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntTranslationRepository extends JpaRepository<InventoryTranslation, Integer> {
}