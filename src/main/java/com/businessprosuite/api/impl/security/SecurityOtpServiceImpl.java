package com.businessprosuite.api.impl.security;

import com.businessprosuite.api.dto.security.SecurityOtpDTO;
import com.businessprosuite.api.model.security.SecurityOtp;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.security.SecurityOtpRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.security.SecurityOtpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityOtpServiceImpl implements SecurityOtpService {

    private final SecurityOtpRepository otpRepo;
    private final SecurityUserRepository userRepo;

    @Override
    public List<SecurityOtpDTO> findAll() {
        return otpRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SecurityOtpDTO findById(Integer id) {
        SecurityOtp otp = otpRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityOtp not found with id " + id));
        return toDto(otp);
    }

    @Override
    public SecurityOtpDTO create(SecurityOtpDTO dto) {
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        SecurityOtp otp = new SecurityOtp();
        otp.setSecus(user);
        otp.setCode(dto.getCode());
        otp.setIssuedAt(dto.getIssuedAt() != null ? dto.getIssuedAt() : LocalDateTime.now());
        otp.setExpiresAt(dto.getExpiresAt());
        otp.setUsed(dto.getUsed() != null ? dto.getUsed() : false);

        SecurityOtp saved = otpRepo.save(otp);
        return toDto(saved);
    }

    @Override
    public SecurityOtpDTO update(Integer id, SecurityOtpDTO dto) {
        SecurityOtp otp = otpRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SecurityOtp not found with id " + id));
        SecurityUser user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));

        otp.setSecus(user);
        otp.setCode(dto.getCode());
        otp.setIssuedAt(dto.getIssuedAt());
        otp.setExpiresAt(dto.getExpiresAt());
        otp.setUsed(dto.getUsed());

        SecurityOtp updated = otpRepo.save(otp);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!otpRepo.existsById(id)) {
            throw new EntityNotFoundException("SecurityOtp not found with id " + id);
        }
        otpRepo.deleteById(id);
    }

    private SecurityOtpDTO toDto(SecurityOtp otp) {
        return SecurityOtpDTO.builder()
                .id(otp.getId())
                .userId(otp.getSecus().getId())
                .code(otp.getCode())
                .issuedAt(otp.getIssuedAt())
                .expiresAt(otp.getExpiresAt())
                .used(otp.getUsed())
                .build();
    }
}
