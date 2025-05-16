package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.finanzas.FinWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinWalletRepository extends JpaRepository<FinWallet, Integer> {
}