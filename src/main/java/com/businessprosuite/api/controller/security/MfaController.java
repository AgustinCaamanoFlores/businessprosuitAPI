package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.security.TotpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mfa")
@RequiredArgsConstructor
public class MfaController {

    private final SecurityUserRepository userRepo;
    private final TotpService totpService;

    @PostMapping("/users/{id}/enable")
    public ResponseEntity<Map<String, String>> enable(@PathVariable Integer id) {
        SecurityUser user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        String secret = totpService.generateSecret();
        user.setSecusMfaEnabled((byte) 1);
        user.setSecusMfaSecret(secret);
        userRepo.save(user);
        Map<String, String> body = new HashMap<>();
        body.put("secret", secret);
        body.put("uri", totpService.getUri(secret, user.getSecusEmail(), "BusinessProSuite"));
        return ResponseEntity.ok(body);
    }

    @PostMapping("/users/{id}/verify")
    public ResponseEntity<Void> verify(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        SecurityUser user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + id));
        String code = payload.get("code");
        if (code == null || !totpService.verifyCode(user.getSecusMfaSecret(), code)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
