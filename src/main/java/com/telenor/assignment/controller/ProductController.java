package com.telenor.assignment.controller;

import com.telenor.assignment.dto.ProductGetDTO;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public ProductGetDTO getProducts(@RequestParam(name = "type", required = false) String type,
                                     @RequestParam(name = "min_price", required = false) BigDecimal min_price,
                                     @RequestParam(name = "max_price", required = false) BigDecimal max_price,
                                     @RequestParam(name = "city", required = false) String city,
                                     @RequestParam(name = "properties", required = false) String properties) {
        List<Product> products = productService.findProducts(type, min_price, max_price, city, properties);
        return ProductGetDTO.builder().data((products)).build();
    }

}
