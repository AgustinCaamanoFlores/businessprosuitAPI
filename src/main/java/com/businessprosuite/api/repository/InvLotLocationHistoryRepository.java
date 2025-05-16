package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventarios.InvLotLocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvLotLocationHistoryRepository extends JpaRepository<InvLotLocationHistory, Integer> {
}