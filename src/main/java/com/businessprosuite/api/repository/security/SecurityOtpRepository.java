// File: src/main/java/com/businessprosuite/api/repository/security/SecurityOtpRepository.java
package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOtpRepository extends JpaRepository<SecurityOtp, Integer> {
    // You can add custom query methods if needed
}