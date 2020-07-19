package com.telenor.assignment.repository.impl;

import com.telenor.assignment.helper.SubProductPropertyManager;
import com.telenor.assignment.model.*;
import com.telenor.assignment.repository.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.telenor.assignment.util.Constants.PHONE_TABLE;
import static com.telenor.assignment.util.Constants.SUBSCRIPTION_TABLE;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    SubProductPropertyManager subProductPropertyManager;


    public List<Product> findProductsWithCriteria(String type, BigDecimal minPrice, BigDecimal maxPrice, String city,
                                                  String color, String property, Integer gbLimitMin,
                                                  Integer gbLimitMax) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Predicate predicate = criteriaBuilder.conjunction();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(productRoot);
        if (!StringUtils.isEmpty(property)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    productRoot.type().in(subProductPropertyManager.getClassListInMapOfClassProperty().get(property))
            );
        }
        if (!StringUtils.isEmpty(city)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.like(productRoot.get(Product_.storeAddress), "%" + city + "%")
            );
        }
        if (Optional.ofNullable(minPrice).orElse(new BigDecimal(0)).compareTo(BigDecimal.ZERO) > 0) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.greaterThanOrEqualTo(productRoot.get(Product_.price), minPrice)
            );
        }
        if (Optional.ofNullable(maxPrice).orElse(new BigDecimal(0)).compareTo(BigDecimal.ZERO) > 0) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.lessThanOrEqualTo(productRoot.get(Product_.price), maxPrice)
            );
        }
        if (!StringUtils.isEmpty(type) && type.equals(PHONE_TABLE)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(productRoot.type(), criteriaBuilder.literal(Phone.class))
            );
        }
        if (!StringUtils.isEmpty(type) && type.equals(SUBSCRIPTION_TABLE)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(productRoot.type(), criteriaBuilder.literal(Subscription.class))
            );
        }
        if (!StringUtils.isEmpty(color)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.and(
                            criteriaBuilder.equal(productRoot.type(), Phone.class),
                            criteriaBuilder.equal(
                                    ((Root<Phone>) (Root<?>) productRoot).get(Phone_.color), color
                            )
                    )
            );
        }
        if (Optional.ofNullable(gbLimitMin).orElse(0) > 0) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.and(
                            criteriaBuilder.equal(productRoot.type(), Subscription.class),
                            criteriaBuilder.greaterThanOrEqualTo(
                                    ((Root<Subscription>) (Root<?>) productRoot).get(Subscription_.gbLimit), gbLimitMin
                            )
                    )
            );
        }
        if (Optional.ofNullable(gbLimitMax).orElse(0) > 0) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.and(
                            criteriaBuilder.equal(productRoot.type(), Subscription.class),
                            criteriaBuilder.lessThanOrEqualTo(
                                    ((Root<Subscription>) (Root<?>) productRoot).get(Subscription_.gbLimit), gbLimitMax
                            )
                    )
            );
        }
        criteriaQuery.where(predicate);
        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
