package com.businessprosuite.api.repository.user;

import com.businessprosuite.api.model.user.UserConsent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConsentRepository extends JpaRepository<UserConsent, Integer> {
}