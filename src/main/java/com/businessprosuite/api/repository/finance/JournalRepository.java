package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Integer> {
}