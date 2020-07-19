package com.telenor.assignment.controller;


import com.telenor.assignment.SpringBootAssignmentApplication;
import com.telenor.assignment.entity.Product;
import com.telenor.assignment.entity.ProductGetAPIDTO;
import com.telenor.assignment.model.helper.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

import static com.telenor.assignment.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Slf4j
@SpringBootTest(classes = SpringBootAssignmentApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testBlankGetRequestProducts_thenReturnHttp200()
    {
        log.info("NILOG::" + "inside testBlankGetRequestProducts");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertSame(productGetAPIDTOResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(100, Objects.requireNonNull(productGetAPIDTOResponseEntity.getBody()).getData().size());
    }



    @Test
    public void testGetRequestProductsWithProductTypeUnknown_thenReturnHttp204()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypeUnknown");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, "unknown");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertEquals(productGetAPIDTOResponseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetRequestProductsWithPropertyTypeUnknown_thenReturnHttp204()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypeUnknown");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "unknown");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertEquals(productGetAPIDTOResponseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    private void assertProduct(ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity, int count) {
        List<Product> products = productGetAPIDTOResponseEntity.getBody().getData();
        log.info("NILOG::" + "productGetAPIDTO.size::" +
                products.size());
        log.info("NILOG::" + "Status Code::" +
                productGetAPIDTOResponseEntity.getStatusCode());
        assertTrue(productGetAPIDTOResponseEntity.getStatusCode() == HttpStatus.OK);
        assertEquals(count,products.size());
        assertAll(products.stream()
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypePhone");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, ProductType.PHONE.getValue());
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class),42);

    }



    @Test
    public void testGetRequestProductsWithProductTypeSubscription()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypeSubscription");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(TYPE, ProductType.SUBSCRIPTION.getValue());
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 58);
    }

    @Test
    public void testGetRequestProductsWithMinPrice()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithMinPrice");
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithMaxPrice");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(MAX_PRICE, "100");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 14);
    }

    @Test
    public void testGetRequestProductsWithMinMaxPrice()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithMinMaxPrice");
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
        log.info("NILOG::" + "inside testGetRequestProductsWithCity");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(CITY, "Karlskrona");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 29);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasColor()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "color");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 42);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasGBLimit()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasGBLimit");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 58);
    }

    @Test
    public void testGetRequestProductsWithPropertyColor()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
               .queryParam(PROPERTY_COLOR, "grön");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 5);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMin()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyGBLimitMin");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 34);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMax()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyGBLimitMax");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithPropertyGBLimitMixMax()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyGBLimitMixMax");
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasGBLimitAndGBLimitMixMax");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "30");
        assertProduct(this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class)
                , 24);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasGBLimitAndColor_expect204()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasGBLimitAndColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "gb_limit")
                .queryParam(PROPERTY_COLOR, "grön");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertEquals(productGetAPIDTOResponseEntity.getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetRequestProductsWithPropertyHasColorAndGBLimitMixMax_expect204()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasColorAndGBLimitMixMax");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(PROPERTY, "color")
                .queryParam(PROPERTY_GB_LIMIT_MIN, "10")
                .queryParam(PROPERTY_GB_LIMIT_MAX, "50");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertEquals(productGetAPIDTOResponseEntity.getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColor()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColor");
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypePhoneAndPropertyHasColorAndColorAndCity");
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMax");
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
        log.info("NILOG::" +
                "inside testGetRequestProductsWithProductTypeSubscriptionPropertyHasGBLimitAndGBLimitMixMaxAndCity");
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

    @Test
    public void testGetRequestProductsWithInvalidPrice_expect400()
    {
        log.info("NILOG::" +
                "inside testGetRequestProductsWithPropertyHasGBLimitAndColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/product")
                .queryParam(MIN_PRICE, "Unknown");
        ResponseEntity<ProductGetAPIDTO> productGetAPIDTOResponseEntity = this.restTemplate
                .getForEntity(builder.toUriString(), ProductGetAPIDTO.class);
        assertEquals(productGetAPIDTOResponseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
    }
}