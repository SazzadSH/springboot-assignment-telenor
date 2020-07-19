package com.telenor.assignment.service;

import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findProductsWithCriteria(ProductType type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                           String color, PropertyType property, Integer gbLimitMin, Integer gbLimitMax);
}
