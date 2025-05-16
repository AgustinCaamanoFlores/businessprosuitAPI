package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.user.UsrConsent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrConsentRepository extends JpaRepository<UsrConsent, Integer> {
}