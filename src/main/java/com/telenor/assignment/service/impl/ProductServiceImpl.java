package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Product;
import com.telenor.assignment.repository.ProductRepository;
import com.telenor.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts(String type, BigDecimal min_price, BigDecimal max_price, String city,
                                         String properties) {
        Specification<Product> productSpecification = (Specification<Product>) (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (!StringUtils.isEmpty(type)) {
                p = cb.and(p, cb.equal(root.get("type"), type));
            }
            if (!StringUtils.isEmpty(city)) {
                p = cb.and(p, cb.like(root.get("storeAddress"), "%" + city + "%"));
            }
            if (!StringUtils.isEmpty(min_price)) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("price"), min_price));
            }
            if (!StringUtils.isEmpty(max_price)) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), max_price));
            }
            if (!StringUtils.isEmpty(properties)) {
                p = cb.and(p, cb.equal(root.get("properties"), properties));
            }
            return p;
        };

        return productRepository.findAll(productSpecification);
    }
}
