package com.businessprosuite.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
})
@Disabled("Context load test disabled")
class BusinessProSuiteApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
