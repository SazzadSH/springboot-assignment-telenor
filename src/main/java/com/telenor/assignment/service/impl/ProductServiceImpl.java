package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Product;
import com.telenor.assignment.repository.ProductDao;
import com.telenor.assignment.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, String property, BigDecimal gbLimitMin,
                                                  BigDecimal gbLimitMax) {
        return productDao.findProductsWithCriteria(type,minPrice,maxPrice,city,color,property,gbLimitMin,gbLimitMax);
    }
}
