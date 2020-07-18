package com.telenor.assignment.service.impl;

import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.Subscription;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.repository.ProductDao;
import com.telenor.assignment.repository.ProductRepository;
import com.telenor.assignment.service.ProductService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDao productDao) {
        this.productRepository = productRepository;
        this.productDao = productDao;
    }

    @Override
    public List<Product> findProducts(String type, BigDecimal minPrice, BigDecimal maxPrice, String city, String color,
                                         String property, BigDecimal gbLimitMin, BigDecimal gbLimitMax) {
        Specification<Product> productSpecification = (Specification<Product>) (productRoot, criteriaQuery,
                                                                                criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (!StringUtils.isEmpty(type)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(productRoot.get("type"), type));
            }
            if (!StringUtils.isEmpty(city)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        productRoot.get("storeAddress"), "%" + city + "%")
                );
            }
            if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                        productRoot.get("price"), minPrice)
                );
            }
            if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(
                        productRoot.get("price"), maxPrice)
                );
            }
            if (!StringUtils.isEmpty(color)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.and(
                        criteriaBuilder.equal(productRoot.type(), Phone.class),
                        criteriaBuilder.equal(((Root<Phone>) (Root<?>) productRoot).get("color"), color)
                ));
            }
            if (gbLimitMin != null && gbLimitMin.compareTo(BigDecimal.ZERO) > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.and(
                        criteriaBuilder.equal(productRoot.type(), Subscription.class),
                        criteriaBuilder.greaterThanOrEqualTo(
                                ((Root<Subscription>) (Root<?>) productRoot).get("gb_limit"), gbLimitMin
                        )
                ));
            }
            if (gbLimitMax != null && gbLimitMax.compareTo(BigDecimal.ZERO) > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.and(
                        criteriaBuilder.equal(productRoot.type(), Subscription.class),
                        criteriaBuilder.lessThanOrEqualTo(
                                ((Root<Subscription>) (Root<?>) productRoot).get("gb_limit"), gbLimitMax
                        )
                ));
            }
            return predicate;
        };

        return productRepository.findAll(productSpecification);
    }

    @Override
    public List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, String property, BigDecimal gbLimitMin,
                                                  BigDecimal gbLimitMax) {
        return productDao.findProductsWithCriteria(type,minPrice,maxPrice,city,color,property,gbLimitMin,gbLimitMax);
    }
}
