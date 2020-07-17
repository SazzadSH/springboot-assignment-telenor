package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.Subscription;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.repository.ProductRepository;
import com.telenor.assignment.service.ProductService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts(String type, BigDecimal minPrice, BigDecimal maxPrice, String city, String color,
                                         String property, BigDecimal gbLimitMin, BigDecimal gbLimitMax) {
        Specification<Product> productSpecification = (Specification<Product>) (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (!StringUtils.isEmpty(type)) {
                p = cb.and(p, cb.equal(root.get("type"), type));
            }
            if (!StringUtils.isEmpty(city)) {
                p = cb.and(p, cb.like(root.get("storeAddress"), "%" + city + "%"));
            }
            if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (!StringUtils.isEmpty(color)) {
                p = cb.and(p, cb.equal(root.get("color"), color));
            }
            if (gbLimitMin != null && gbLimitMin.compareTo(BigDecimal.ZERO) < 0) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("gb_limit"), gbLimitMin));
            }
            if (gbLimitMax != null && gbLimitMax.compareTo(BigDecimal.ZERO) < 0) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("gb_limit"), gbLimitMax));
            }
            return p;
        };

        return productRepository.findAll(productSpecification);
    }

    @Override
    public List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city, String color,
                                      String property, BigDecimal gbLimitMin, BigDecimal gbLimitMax) {
        DetachedCriteria productCritera = DetachedCriteria.forClass(Product.class);
        DetachedCriteria phoneCritera = DetachedCriteria.forClass(Phone.class);
        DetachedCriteria subscriptionCritera = DetachedCriteria.forClass(Subscription.class);

        return null;
    }
}
