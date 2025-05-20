package com.businessprosuite.api.repository.core;

import com.businessprosuite.api.model.core.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<Translation, Integer> {
}