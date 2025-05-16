package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.configuraciones.CfgEncryptionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgEncryptionKeyRepository extends JpaRepository<CfgEncryptionKey, Integer> {
}