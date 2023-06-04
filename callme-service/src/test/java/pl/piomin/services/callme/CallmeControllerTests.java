package pl.piomin.services.callme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.application.name=xxx",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.cloud.consul.discovery.fail-fast=false",
        "spring.cloud.consul.config.enabled=false",
        "spring.cloud.consul.discovery.catalogServicesWatch.enabled=false",
        "VERSION: v1"
    }
)
public class CallmeControllerTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void call() {
        String res = restTemplate.getForObject("/callme", String.class);
        assertNotNull(res);
        assertEquals("I'm Callme running on port " + port, res);
    }

    @Test
    void slow() {
        String res = restTemplate.getForObject("/callme/slow", String.class);
        assertNotNull(res);
        assertEquals("I'm Slow Callme running on port " + port, res);
    }
}
