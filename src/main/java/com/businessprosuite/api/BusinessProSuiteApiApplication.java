package com.businessprosuite.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EntityScan("com.businessprosuite.api.model")
@EnableJpaRepositories("com.businessprosuite.api.repository")
public class BusinessProSuiteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProSuiteApiApplication.class, args);
    }

}
