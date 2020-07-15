package com.telenor.assignment;


import com.telenor.assignment.entity.ProductGetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootAssignmentApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)/*
@TestPropertySource(value={"classpath:application-dev.properties"})*/
//@ActiveProfiles("dev")
class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({ "classpath:schema.sql", "classpath:data.sql" })
    @Test
    public void testProducts()
    {
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/api/products", ProductGetDTO.class)
                        .getData().size() == 100);
    }



}
