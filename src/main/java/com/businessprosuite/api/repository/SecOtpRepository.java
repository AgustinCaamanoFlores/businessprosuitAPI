package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.security.SecOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecOtpRepository extends JpaRepository<SecOtp, Integer> {
}