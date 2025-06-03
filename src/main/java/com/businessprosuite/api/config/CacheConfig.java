package com.businessprosuite.api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Configuración de cache usando Caffeine para mejorar el rendimiento
 * de consultas frecuentes y reducir la carga en la base de datos.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configuración del cache manager con diferentes configuraciones
     * para distintos tipos de datos.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // Configuración por defecto
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .recordStats());

        // Caches específicos
        cacheManager.setCacheNames(Arrays.asList(
                "userCache",           // Cache de usuarios
                "companyCache",        // Cache de empresas
                "roleCache",           // Cache de roles
                "configCache",         // Cache de configuraciones
                "menuCache",           // Cache de menús
                "permissionCache",     // Cache de permisos
                "shortLivedCache",     // Cache de corta duración
                "longLivedCache"       // Cache de larga duración
        ));

        return cacheManager;
    }

    /**
     * Cache para datos que cambian frecuentemente
     */
    @Bean
    public Caffeine<Object, Object> shortLivedCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats();
    }

    /**
     * Cache para datos que raramente cambian
     */
    @Bean
    public Caffeine<Object, Object> longLivedCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(2, TimeUnit.HOURS)
                .recordStats();
    }

    /**
     * Cache específico para usuarios con configuración optimizada
     */
    @Bean
    public Caffeine<Object, Object> userCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats();
    }
} 