package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecOtpRepository extends JpaRepository<SecurityOtp, Integer> {
}