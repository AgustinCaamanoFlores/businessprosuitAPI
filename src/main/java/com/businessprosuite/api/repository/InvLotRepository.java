package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.inventarios.InvLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvLotRepository extends JpaRepository<InvLot, Integer> {
}