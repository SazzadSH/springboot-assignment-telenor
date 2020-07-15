package com.telenor.assignment;


import com.telenor.assignment.entity.ProductGetAPIDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootAssignmentApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testProducts()
    {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/api/products", ProductGetAPIDTO.class)
                        .getData().size() == 100);
    }



}
