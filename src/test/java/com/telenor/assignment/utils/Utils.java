package com.telenor.assignment.utils;

import com.telenor.assignment.dto.ProductGetDTO;
import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Product;
import com.telenor.assignment.model.Subscription;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Utils {
    public static void assertResult(List<Product> result){
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(new BigDecimal(100.00));
        assertThat(result.get(1).getStoreAddress())
                .isEqualTo("Gustafsson gärdet, Malmö");
    }
    public static void assertResult(ProductGetDTO result){
        assertThat(result.getData().size()).isEqualTo(2);
        assertThat(result.getData().get(0).getPrice())
                .isEqualTo(new BigDecimal(100.00));
        assertThat(result.getData().get(1).getStoreAddress())
                .isEqualTo("Gustafsson gärdet, Malmö");
    }
    public static List<Product> prepareMockData() {
        Phone phone = new Phone();
        phone.setPrice(new BigDecimal(100.00));
        phone.setColor("grön");
        phone.setStoreAddress("Nilsson gatan, Stockholm");
        Subscription subscription = new Subscription();
        subscription.setPrice(new BigDecimal(114.00));
        subscription.setGbLimit(new Integer(10));
        subscription.setStoreAddress("Gustafsson gärdet, Malmö");
        List<Product> productList = new ArrayList<>(Arrays.asList(phone, subscription));
        return productList;
    }
}
