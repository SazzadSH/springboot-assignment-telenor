package com.telenor.assignment;


import com.telenor.assignment.entity.ProductGetAPIDTO;
import com.telenor.assignment.model.helper.ProductType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.telenor.assignment.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest(classes = SpringBootAssignmentApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class ProductControllerIntegrationTests {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testBlankGetRequestProducts()
    {
        logger.info(new StringBuffer().append("NILOG::").append("inside testBlankGetRequestProducts").toString());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertTrue(productGetAPIDTOResponseEntity.getStatusCode() == HttpStatus.OK);
        assertEquals(100,productGetAPIDTOResponseEntity.getBody().getData().size());
    }

    private void assertProduct(ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity, int count) {
        logger.info(new StringBuffer().append("NILOG::").append("productGetAPIDTO.size::")
                .append(productGetAPIDTOResponseEntity.getBody().getData().size()).toString());
        logger.info(new StringBuffer().append("NILOG::").append("Status Code::")
                .append(productGetAPIDTOResponseEntity.getStatusCode()).toString());
        assertTrue(productGetAPIDTOResponseEntity.getStatusCode() == HttpStatus.OK);
        assertEquals(count,productGetAPIDTOResponseEntity.getBody().getData().size());
        assertAll(productGetAPIDTOResponseEntity.getBody().getData().stream()
                .map(item->
                        ()-> {
                            assumingThat((item != null),
                                    () -> {
                                        assertNotNull(item.getType());
                                        assertNotEquals(item.getPrice(),0.0);
                                        assertNotNull(item.getStoreAddress());
                                        if(item.getType().equals(ProductType.PHONE.getValue())){
                                            assertTrue(item.getType().equals(ProductType.PHONE.getValue()));
                                            assertTrue(item.getProperties().startsWith("color:"));
                                        }else if(item.getType().equals(ProductType.SUBSCRIPTION.getValue())){
                                            assertTrue(item.getType().equals(ProductType.SUBSCRIPTION.getValue()));
                                            assertTrue(item.getProperties().startsWith("gb_limit:"));
                                        }else{
                                            fail("Unknown type.");
                                        }
                                    }
                            );
                        }
                ));

    }

    @Test
    public void testGetRequestProductsWithProductTypePhone()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypePhone").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, ProductType.PHONE.getValue());
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class),42);

    }



    @Test
    public void testGetRequestProductsWithProductTypeSubscription()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypeSubscription").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, ProductType.SUBSCRIPTION.getValue());
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 58);
    }

    @Test
    public void testGetRequestProductsWithMinPrice()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithMinPrice").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(MIN_PRICE, "100");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertTrue(productGetAPIDTOResponseEntity.getStatusCode() == HttpStatus.OK);
        assertEquals(productGetAPIDTOResponseEntity.getBody().getData().size(), 86);
    }

    @Test
    public void testGetRequestProductsWithMaxPrice()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithMaxPrice").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(MAX_PRICE, "100");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 14);
    }

    @Test
    public void testGetRequestProductsWithMinMaxPrice()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithMinMaxPrice").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(MIN_PRICE, "100")
                .queryParam(MAX_PRICE, "200");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 8);
    }

    @Test
    public void testGetRequestProductsWithCity()
    {
        logger.info(new StringBuffer().append("NILOG::").append("inside testGetRequestProductsWithCity").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(CITY, "Karlskrona");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 29);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasColor()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyHasColor").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "color");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 42);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasGBLimit()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyHasGBLimit").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 58);
    }

    @Test
    public void testGetRequestProductsWithPropertyColor()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyColor").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
               .queryParam(PROPERTY_COLOR, "grön");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 5);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMin()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyGBLimitMin").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 34);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMax()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyGBLimitMax").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMixMax()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyGBLimitMixMax").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasGBLimitAndGBLimitMixMax()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyHasGBLimitAndGBLimitMixMax").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasGBLimitAndColor()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyHasGBLimitAndColor").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_COLOR, "grön");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 0);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasColorAndGBLimitMixMax()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithPropertyHasColorAndGBLimitMixMax").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "color")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "50");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 0);
    }

    @Test
    public void testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColor()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColor").toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, "phone")
                .queryParam(PROPERTY, "color")
                .queryParam(PROPERTY_COLOR, "grön");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 5);
    }

    @Test
    public void testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColorAndCity()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColorAndCity")
                .toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, "phone")
                .queryParam(CITY, "Stockholm")
                .queryParam(PROPERTY, "color")
                .queryParam(PROPERTY_COLOR,"grön");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 2);
    }

    @Test
    public void testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMax()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMax")
                .toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, "subscription")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMaxAndCity()
    {
        logger.info(new StringBuffer().append("NILOG::")
                .append("inside testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMaxAndCity")
                .toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, "subscription")
                .queryParam(CITY, "Stockholm")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 10);
    }

}