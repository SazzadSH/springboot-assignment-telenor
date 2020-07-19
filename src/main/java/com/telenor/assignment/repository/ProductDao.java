package com.telenor.assignment.repository;


import com.telenor.assignment.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {

    public List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, String property, Integer gbLimitMin,
                                                  Integer gbLimitMax);
}
