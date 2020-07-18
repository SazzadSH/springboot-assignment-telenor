package com.telenor.assignment.controller;

import com.telenor.assignment.dto.ProductGetDTO;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.telenor.assignment.util.Constants.*;

@RestController
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProductController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product")
    @ApiOperation(
            value = "Get Product information.",
            notes = "Get Product information.",
            response = ProductGetDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SUCCESS_REQUEST, response = ProductGetDTO.class),
            @ApiResponse(code = 404, message = RESOURCE_NOT_FOUND_ERROR),
            @ApiResponse(code = 404, message = BAD_REQUEST),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR)
    })
    public ProductGetDTO getProducts(
            @ApiParam(value = "The product type. (String. Can be 'phone' or 'subscription')",
                    allowableValues = "phone,subscription")
            @RequestParam(name = "type", required = false) String type,
            @ApiParam(value = "The minimum price in SEK. (Number)")
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @ApiParam(value = "The maximum price in SEK. (Number)")
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            @ApiParam(value = "The city in which a store is located. (String)")
            @RequestParam(name = "city", required = false) String city,
            @ApiParam(value = "The name of the property. (String. Can be 'color' or 'gb_limit')")
            @RequestParam(name = "property", required = false) String property,
            @ApiParam(value = "The color of the phone. (String)")
            @RequestParam(name = "property.color", required = false) String color,
            @ApiParam(value = "The minimum GB limit of the subscription. (Number)")
            @RequestParam(name = "property.gb_limit_min", required = false) BigDecimal gbLimitMin,
            @ApiParam(value = "The maximum GB limit of the subscription. (Number)")
            @RequestParam(name = "property.gb_limit_max", required = false) BigDecimal gbLimitMax
    ) {
        List<Product> products = productService.findProductsWithCriteria(type, minPrice, maxPrice, city, color,
                property, gbLimitMin, gbLimitMax);
        return ProductGetDTO.builder().data((products)).build();
    }
}
