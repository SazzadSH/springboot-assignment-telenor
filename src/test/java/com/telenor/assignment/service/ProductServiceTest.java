package com.telenor.assignment.service;

import com.telenor.assignment.utils.Utils;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.repository.ProductDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(ProductService.class)
public class ProductServiceTest {
    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    static List<Product> productList;

    @BeforeAll
    public static void prepareMockData() {
        productList = Utils.prepareMockData();
    }

    @Test
    public void findProductsWithCriteriaWithOutAnyThing(){
        given(productDao.findProductsWithCriteria(null,null,null,null,
                null,null,null,null))
                .willReturn(productList);
        List<Product> result = productService.findProductsWithCriteria(null,null,null,null,
                null, null, null, null);
        Utils.assertResult(result);
    }

    @Test
    public void findProductsWithCriteriaWithProductType(){
        given(productDao.findProductsWithCriteria(ProductType.PHONE.getValue(),null,null,null,
                null,null,null,null))
                .willReturn(productList);
        List<Product> result = productService.findProductsWithCriteria(ProductType.PHONE,null,null,
                null,null, null, null, null);
        Utils.assertResult(result);
    }

    @Test
    public void findProductsWithCriteriaWithPropertyType(){
        given(productDao.findProductsWithCriteria(null,null,null,null,
                null, PropertyType.COLOR.getValue(),null,null))
                .willReturn(productList);
        List<Product> result = productService.findProductsWithCriteria(null,null,null,null,
                null, PropertyType.COLOR, null, null);
        Utils.assertResult(result);
    }

    @Test
    public void findProductsWithCriteriaWithProductTypeAndPropertyType(){
        given(productDao.findProductsWithCriteria(ProductType.PHONE.getValue(),null,null,null,
                null, PropertyType.COLOR.getValue(),null,null))
                .willReturn(productList);
        List<Product> result = productService.findProductsWithCriteria(ProductType.PHONE,null,null,
                null,null, PropertyType.COLOR, null, null);
        Utils.assertResult(result);
    }
}
