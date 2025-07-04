package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.service.security.TotpService;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.stereotype.Service;

@Service
public class TotpServiceImpl implements TotpService {
    @Override
    public String generateSecret() {
        return Base32.random();
    }

    @Override
    public String getUri(String secret, String account, String issuer) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer, account, secret, issuer);
    }

    @Override
    public boolean verifyCode(String secret, String code) {
        Totp totp = new Totp(secret);
        return totp.verify(code);
    }
}
