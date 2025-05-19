package com.businessprosuite.api.repository.config;

import com.businessprosuite.api.model.config.ConfigEncryptionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigEncryptionKeyRepository extends JpaRepository<ConfigEncryptionKey, Integer> {
}