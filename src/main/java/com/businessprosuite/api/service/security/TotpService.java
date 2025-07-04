package com.businessprosuite.api.service.security;

public interface TotpService {
    String generateSecret();
    String getUri(String secret, String account, String issuer);
    boolean verifyCode(String secret, String code);
}
