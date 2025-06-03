package com.businessprosuite.api.repository.security;

import com.businessprosuite.api.model.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {
    
    /**
     * Busca un usuario por su nombre de usuario
     */
    Optional<SecurityUser> findBySecusName(String secusName);
    
    /**
     * Busca un usuario por su email
     */
    Optional<SecurityUser> findBySecusEmail(String secusEmail);
    
    /**
     * Verifica si existe un usuario con el nombre dado
     */
    boolean existsBySecusName(String secusName);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsBySecusEmail(String secusEmail);
}