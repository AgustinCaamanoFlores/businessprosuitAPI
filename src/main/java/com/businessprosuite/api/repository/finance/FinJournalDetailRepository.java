package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.JournalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinJournalDetailRepository extends JpaRepository<JournalDetail, Integer> {
}