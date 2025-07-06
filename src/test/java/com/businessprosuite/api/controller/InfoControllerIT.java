package com.businessprosuite.api.controller;

import com.businessprosuite.api.controller.info.InfoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.businessprosuite.api.config.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InfoController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class
        },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "app.version=1.2.3",
        "app.description=Test API",
        "app.environment=test",
        "app.name=MyAPI",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.JpaRepositoriesAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class InfoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVersion_returnsOk() throws Exception {
        mockMvc.perform(get("/api/version"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.2.3"));
    }

    @Test
    void getInfo_returnsOk() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.2.3"))
                .andExpect(jsonPath("$.description").value("Test API"))
                .andExpect(jsonPath("$.environment").value("test"))
                .andExpect(jsonPath("$.name").value("MyAPI"));
    }
}
