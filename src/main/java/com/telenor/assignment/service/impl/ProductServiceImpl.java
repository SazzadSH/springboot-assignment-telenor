package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.repository.ProductDao;
import com.telenor.assignment.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
    public List<Product> findProductsWithCriteria(ProductType type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, PropertyType property, Integer gbLimitMin,
                                                  Integer gbLimitMax) {
        String typeAsString = null;
        if(type!=null){
            typeAsString = type.getStringValue();
        }
        String propertyAsString = null;
        if(property!=null){
            propertyAsString = property.getStringValue();
        }
        return productDao.findProductsWithCriteria(typeAsString,minPrice,maxPrice,city,color,
                                                        propertyAsString,gbLimitMin,gbLimitMax);
    }
}
