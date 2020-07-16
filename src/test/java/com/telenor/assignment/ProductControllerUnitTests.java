package com.telenor.assignment;

import com.telenor.assignment.controller.ProductController;
import com.telenor.assignment.dto.ProductGetDTO;
import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.Subscription;
import com.telenor.assignment.model.helper.ProductType;
import com.telenor.assignment.repository.ProductRepository;
import com.telenor.assignment.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerUnitTests {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    public void getProducts()
    {
        // given a pre defined list
        Phone phone = new Phone();
        phone.setPrice(new BigDecimal(100.00));
        phone.setColor("grön");
        phone.setStoreAddress("Nilsson gatan, Stockholm");
        Subscription subscription = new Subscription();
        subscription.setPrice(new BigDecimal(114.00));
        subscription.setGbLimit(new BigDecimal(10));
        subscription.setStoreAddress("Gustafsson gärdet, Malmö");

        List<Product> productList = new ArrayList<Product>();
        productList.addAll(Arrays.asList(phone, subscription));

        when(productService.findProducts(null,null,null,null,null, null,
                null)).thenReturn(productList);

        // when
        ProductGetDTO result = productController.getProducts(null,null,null,null,
                null, null, null );

        // then check the output and assert
        assertThat(result.getData().size()).isEqualTo(2);

        assertThat(result.getData().get(0).getPrice())
                .isEqualTo(phone.getPrice());

        assertThat(result.getData().get(1).getStoreAddress())
                .isEqualTo(subscription.getStoreAddress());
    }
}
