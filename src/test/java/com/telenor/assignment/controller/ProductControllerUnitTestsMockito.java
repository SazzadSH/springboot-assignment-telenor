package com.telenor.assignment.controller;

import com.telenor.assignment.utils.Utils;
import com.telenor.assignment.dto.ProductGetDTO;

import com.telenor.assignment.expection.IllegalProductTypeException;
import com.telenor.assignment.expection.IllegalPropertyTypeException;
import com.telenor.assignment.model.Product;

import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerUnitTestsMockito {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    static List<Product> productList;

    @BeforeAll
    public static void prepareMockData() {
        productList = Utils.prepareMockData();
    }

    @Test
    public void getProductsWithoutParam()
    {
        when(
                productService.findProductsWithCriteria(null,null,null,null,
                        null, null, null, null)
        ).thenReturn(productList);
        // when
        ProductGetDTO result = productController.getProducts(null,null,null,null,
                null, null, null, null);

        Utils.assertResult(result);
    }

    @Test
    public void getProductsWithCity()
    {
        when(
                productService.findProductsWithCriteria(null,null,null,"Stockholm",
                        null, null, null, null)
        ).thenReturn(productList);
        // when
        ProductGetDTO result = productController.getProducts(null,null,null,"Stockholm",
                null, null, null, null);
        Utils.assertResult(result);
    }

    @Test
    public void getProductsWithProductType()
    {
        when(
                productService.findProductsWithCriteria(ProductType.PHONE,null,null,"Stockholm",
                        null, null, null, null)
        ).thenReturn(productList);
        // when
        ProductGetDTO result = productController.getProducts(ProductType.PHONE,null,null,"Stockholm",
                null, null, null, null);
        Utils.assertResult(result);
    }

    @Test
    public void getProductsWithPropertyTypeAndProductType()
    {
        when(
                productService.findProductsWithCriteria(ProductType.PHONE,null,null,null,
                        null , PropertyType.COLOR, null, null)
        ).thenReturn(productList);
        // when
        ProductGetDTO result = productController.getProducts(ProductType.PHONE,null,null,null,
                PropertyType.COLOR, null, null, null);
        // then check the output and assert
        Utils.assertResult(result);
    }


}
