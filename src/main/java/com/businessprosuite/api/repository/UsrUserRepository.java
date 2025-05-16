package com.businessprosuite.api.repository;

import com.businessprosuite.api.model.user.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrUserRepository extends JpaRepository<UsrUser, Integer> {
}