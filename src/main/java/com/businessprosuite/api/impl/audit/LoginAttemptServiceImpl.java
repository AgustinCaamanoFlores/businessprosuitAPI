package com.businessprosuite.api.impl.audit;

import com.businessprosuite.api.dto.audit.LoginAttemptDTO;
import com.businessprosuite.api.model.audit.LoginAttempt;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.audit.LoginAttemptRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.audit.LoginAttemptService;
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
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final LoginAttemptRepository repo;
    private final SecurityUserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<LoginAttemptDTO> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LoginAttemptDTO findById(Integer id) {
        LoginAttempt la = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoginAttempt not found with id " + id));
        return toDto(la);
    }

    @Override
    public LoginAttemptDTO create(LoginAttemptDTO dto) {
        SecurityUser user = null;
        if (dto.getUserId() != null) {
            user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
        }
        LoginAttempt la = new LoginAttempt();
        la.setAulaUser(user);
        la.setAulaAttemptTime(dto.getAttemptTime() != null ? dto.getAttemptTime() : LocalDateTime.now());
        la.setAulaIp(dto.getIp());
        la.setAulaSuccess(dto.getSuccess() ? (byte)1 : (byte)0);
        LoginAttempt saved = repo.save(la);
        return toDto(saved);
    }

    @Override
    public LoginAttemptDTO update(Integer id, LoginAttemptDTO dto) {
        LoginAttempt la = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoginAttempt not found with id " + id));
        if (dto.getUserId() != null) {
            SecurityUser user = userRepo.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getUserId()));
            la.setAulaUser(user);
        }
        if (dto.getAttemptTime() != null) {
            la.setAulaAttemptTime(dto.getAttemptTime());
        }
        la.setAulaIp(dto.getIp());
        la.setAulaSuccess(dto.getSuccess() ? (byte)1 : (byte)0);
        LoginAttempt updated = repo.save(la);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("LoginAttempt not found with id " + id);
        }
        repo.deleteById(id);
    }

    private LoginAttemptDTO toDto(LoginAttempt la) {
        Integer userId = la.getAulaUser() != null ? la.getAulaUser().getId() : null;
        return LoginAttemptDTO.builder()
                .id(la.getId())
                .userId(userId)
                .attemptTime(la.getAulaAttemptTime())
                .ip(la.getAulaIp())
                .success(la.getAulaSuccess() != 0)
                .build();
    }
}