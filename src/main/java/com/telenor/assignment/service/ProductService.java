package com.telenor.assignment.service;

import com.telenor.assignment.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findProducts(String type, BigDecimal min_price, BigDecimal max_price, String city, String properties);
}
