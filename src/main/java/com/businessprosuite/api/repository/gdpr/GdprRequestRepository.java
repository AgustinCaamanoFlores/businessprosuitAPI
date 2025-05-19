package com.businessprosuite.api.repository.gdpr;

import com.businessprosuite.api.model.gdpr.GDPRRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GdprRequestRepository extends JpaRepository<GDPRRequest, Integer> {
}