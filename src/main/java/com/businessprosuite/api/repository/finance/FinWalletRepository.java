package com.businessprosuite.api.repository.finance;

import com.businessprosuite.api.model.finance.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinWalletRepository extends JpaRepository<Wallet, Integer> {
}