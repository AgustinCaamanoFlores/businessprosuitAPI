package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinJournal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinJournalRepository extends JpaRepository<FinJournal, Integer> {
}