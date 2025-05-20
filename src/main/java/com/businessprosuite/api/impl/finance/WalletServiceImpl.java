package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.WalletDTO;
import com.businessprosuite.api.model.finance.Wallet;
import com.businessprosuite.api.model.finance.Currency;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.finance.WalletRepository;
import com.businessprosuite.api.repository.finance.CurrencyRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.finance.WalletService;
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
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepo;
    private final SecurityUserRepository userRepo;
    private final CurrencyRepository currencyRepo;

    @Override
    public List<WalletDTO> findAll() {
        return walletRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WalletDTO findById(Integer id) {
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with id " + id));
        return toDto(wallet);
    }

    @Override
    public WalletDTO create(WalletDTO dto) {
        SecurityUser user = userRepo.findById(dto.getSecurityUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getSecurityUserId()));
        Currency currency = currencyRepo.findById(dto.getCurrencyCode())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with code " + dto.getCurrencyCode()));

        Wallet wallet = new Wallet();
        wallet.setFinWltSecus(user);
        wallet.setFinWltBalance(dto.getBalance());
        wallet.setFinWltCurCode(currency);
        wallet.setFinWltStatus(dto.getStatus());
        wallet.setFinWltCreatedAt(LocalDateTime.now());
        wallet.setFinWltUpdatedAt(LocalDateTime.now());

        Wallet saved = walletRepo.save(wallet);
        return toDto(saved);
    }

    @Override
    public WalletDTO update(Integer id, WalletDTO dto) {
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with id " + id));
        SecurityUser user = userRepo.findById(dto.getSecurityUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getSecurityUserId()));
        Currency currency = currencyRepo.findById(dto.getCurrencyCode())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found with code " + dto.getCurrencyCode()));

        wallet.setFinWltSecus(user);
        wallet.setFinWltBalance(dto.getBalance());
        wallet.setFinWltCurCode(currency);
        wallet.setFinWltStatus(dto.getStatus());
        wallet.setFinWltUpdatedAt(LocalDateTime.now());

        Wallet updated = walletRepo.save(wallet);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!walletRepo.existsById(id)) {
            throw new EntityNotFoundException("Wallet not found with id " + id);
        }
        walletRepo.deleteById(id);
    }

    private WalletDTO toDto(Wallet wallet) {
        return WalletDTO.builder()
                .id(wallet.getId())
                .securityUserId(wallet.getFinWltSecus().getId())
                .balance(wallet.getFinWltBalance())
                .currencyCode(wallet.getFinWltCurCode().getFinCurCode())
                .status(wallet.getFinWltStatus())
                .createdAt(wallet.getFinWltCreatedAt())
                .updatedAt(wallet.getFinWltUpdatedAt())
                .build();
    }
}