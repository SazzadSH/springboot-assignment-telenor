package com.telenor.assignment;

import com.telenor.assignment.controller.ProductController;
import com.telenor.assignment.dto.ProductGetDTO;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.repository.ProductRepository;
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
    ProductRepository productRepository;

    @Test
    public void getProducts()
    {
        // given a pre fined list
        Product product1 = new Product();
        product1.setPrice(new BigDecimal(100.00));
        product1.setProperties("color:grön");
        product1.setStoreAddress("Nilsson gatan, Stockholm");
        product1.setType("phone");
        Product product2 = new Product();
        product2.setPrice(new BigDecimal(114.00));
        product2.setProperties("gb_limit:10");
        product2.setStoreAddress("Gustafsson gärdet, Malmö");
        product2.setType("subscription");

        List<Product> productList = new ArrayList<Product>();
        productList.addAll(Arrays.asList(product1, product2));

       /* Specification<Product> productSpecification = (Specification<Product>) (root, query, cb) -> {
            return cb.conjunction();
        };*/

        when(productRepository.findAll()).thenReturn(productList);

        // when
        ProductGetDTO result = productController.getProducts(null,null,null,null,null);

        // then
        assertThat(result.getData().size()).isEqualTo(2);

        assertThat(result.getData().get(0).getPrice())
                .isEqualTo(product1.getPrice());

        assertThat(result.getData().get(1).getType())
                .isEqualTo(product2.getType());
    }
}
