package pl.piomin.services.caller;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit5.HoverflyExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.application.name=xxx",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.cloud.consul.discovery.fail-fast=false",
        "spring.cloud.consul.config.enabled=false",
        "spring.cloud.consul.discovery.catalogServicesWatch.enabled=false",
        "VERSION = v2"
    }
)
@ExtendWith(HoverflyExtension.class)
public class CallerCallmeTest {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void callmeIntegration(Hoverfly hoverfly) {
        hoverfly.simulate(
                dsl(service("http://callme-service")
                        .get("/callme")
                        .willReturn(success().body("I'm callme-service v1.")))
        );
        String response = restTemplate.getForObject("/caller", String.class);
//        assertEquals("I'm Caller running on port " + port
//                + " calling-> I'm callme-service v1.", response);
    }
}
