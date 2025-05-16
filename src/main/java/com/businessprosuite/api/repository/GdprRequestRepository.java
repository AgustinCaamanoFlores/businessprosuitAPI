package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.GdprRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GdprRequestRepository extends JpaRepository<GdprRequest, Integer> {
}