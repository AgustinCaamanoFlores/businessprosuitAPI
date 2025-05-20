package com.businessprosuite.api.repository.gdpr;

import com.businessprosuite.api.model.gdpr.GDPRRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GDPRRequestRepository extends JpaRepository<GDPRRequest, Integer> {
}