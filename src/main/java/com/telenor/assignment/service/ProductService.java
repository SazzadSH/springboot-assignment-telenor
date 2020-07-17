package com.telenor.assignment.service;

import com.telenor.assignment.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findProducts(String type, BigDecimal minPrice, BigDecimal maxPrice, String city, String color,
                               String property, BigDecimal gbLimitMin, BigDecimal gbLimitMax);
    List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city, String color,
                               String property, BigDecimal gbLimitMin, BigDecimal gbLimitMax);
}
