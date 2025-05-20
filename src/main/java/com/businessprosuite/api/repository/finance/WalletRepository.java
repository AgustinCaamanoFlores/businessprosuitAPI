package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}