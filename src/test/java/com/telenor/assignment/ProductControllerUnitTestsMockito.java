package com.telenor.assignment;

import com.telenor.assignment.controller.ProductController;
import com.telenor.assignment.dto.ProductGetDTO;

import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.Subscription;

import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.model.helper.PropertyType;
import com.telenor.assignment.service.ProductService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telenor.assignment.util.Constants.PHONE_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerUnitTestsMockito {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    static List<Product> productList = new ArrayList<Product>();

    @BeforeAll
    public static void mockTheRepository() {
        // given a pre defined list
        Phone phone = new Phone();
        phone.setPrice(new BigDecimal(100.00));
        phone.setColor("grön");
        phone.setStoreAddress("Nilsson gatan, Stockholm");
        Subscription subscription = new Subscription();
        subscription.setPrice(new BigDecimal(114.00));
        subscription.setGbLimit(new Integer(10));
        subscription.setStoreAddress("Gustafsson gärdet, Malmö");
        productList.addAll(Arrays.asList(phone, subscription));
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

        // then check the output and assert
        assertThat(result.getData().size()).isEqualTo(2);

        assertThat(result.getData().get(0).getPrice())
                .isEqualTo(new BigDecimal(100.00));

        assertThat(result.getData().get(1).getStoreAddress())
                .isEqualTo("Gustafsson gärdet, Malmö");
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
        // then check the output and assert
        assertThat(result.getData().size()).isEqualTo(2);
        assertThat(result.getData().get(1).getStoreAddress())
                .isEqualTo("Gustafsson gärdet, Malmö");
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
        // then check the output and assert
        assertThat(result.getData().size()).isEqualTo(2);
        assertThat(result.getData().get(1).getStoreAddress())
                .isEqualTo("Gustafsson gärdet, Malmö");
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
        assertThat(result.getData().size()).isEqualTo(2);
        assertThat(result.getData().get(0).getType())
                .isEqualTo(PHONE_TABLE);
        assertThat(result.getData().get(0).getProperties())
                .startsWith("color");
    }
}
