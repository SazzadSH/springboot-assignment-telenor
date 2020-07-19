package com.telenor.assignment.repository;

import com.telenor.assignment.helper.SubProductPropertyManager;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan("com.telenor.assignment")
@ActiveProfiles("dev")
public class ProductRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    SubProductPropertyManager subProductPropertyManager;
    @Autowired
    private ProductDao productDao;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(productDao).isNotNull();
    }

    @Test
    void findProductsWithCriteria_expectNotNull() {

        List<Product> productList = productDao.findProductsWithCriteria(null,null,null,
                null,null,null,null,null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),100);
    }

    @Test
    void findProductsWithCriteriaWithProductType_expect42() {
        List<Product> productList = productDao.findProductsWithCriteria(ProductType.PHONE.getValue(),null,
                null,null,null,null,null,null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),42);
    }

    @Test
    void findProductsWithCriteriaWithPropertyType_expect42() {
        List<Product> productList = productDao.findProductsWithCriteria(null,null,null,null,
                null,PropertyType.COLOR.getValue(),null,null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),42);
    }

    @Test
    void findProductsWithCriteriaWithPropertyTypeAndColor_expect5() {
        List<Product> productList = productDao.findProductsWithCriteria(null,null,null,null,
                "grön",PropertyType.COLOR.getValue(),null,null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),5);
    }

    @Test
    void findProductsWithCriteriaWithMinAndMaxPrice_expect8() {
        BigDecimal min = new BigDecimal(100);
        BigDecimal max = new BigDecimal(200);
        List<Product> productList = productDao.findProductsWithCriteria(null,min,
                max,null,null,null,null,
                null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),8);
        productList.forEach(item->{
            MatcherAssert.assertThat(item.getPrice(),allOf(greaterThanOrEqualTo(min), lessThanOrEqualTo(max)));
        });
    }

    @Test
    void findProductsWithCriteriaWithCity_expect29() {
        String city = "Karlskrona";
        List<Product> productList = productDao.findProductsWithCriteria(null,null,
                null,city,null,null,null,
                null);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),29);
        productList.forEach(item->{
            MatcherAssert.assertThat(item.getStoreAddress(),containsString(city));
        });
    }

    @Test
    void findProductsWithCriteriaWithPropertyTypeGBLimitAndMinMaxGBLimit_expect24() {
        Integer min = new Integer(10);
        Integer max = new Integer(30);
        List<Product> productList = productDao.findProductsWithCriteria(null,null,
                null,null,null,PropertyType.GB_LIMIT.getValue(),min,
                max);
        assertThat(productList).isNotNull();
        assertEquals(productList.size(),24);
    }
}
