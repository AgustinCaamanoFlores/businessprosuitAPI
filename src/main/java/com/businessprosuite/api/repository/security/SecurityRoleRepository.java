package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Integer> {
    
    /**
     * Busca un rol por su nombre
     */
    Optional<SecurityRole> findBySecrlName(String secrlName);
}