package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.repository.ProductDao;
import com.telenor.assignment.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findProductsWithCriteria(ProductType type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, PropertyType property, Integer gbLimitMin,
                                                  Integer gbLimitMax) {
        String typeAsString = null;
        if(type!=null){
            typeAsString = type.getValue();
        }
        String propertyAsString = null;
        if(property!=null){
            propertyAsString = property.getValue();
        }
        return productDao.findProductsWithCriteria(typeAsString,minPrice,maxPrice,city,color,
                                                        propertyAsString,gbLimitMin,gbLimitMax);
    }
}
