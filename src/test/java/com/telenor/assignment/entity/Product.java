package com.telenor.assignment.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.telenor.assignment.model.helper.ProductType;
import lombok.Data;


@Data
@JsonAutoDetect
public class Product {
    private Long id;
    private String type;
    private Double price;
    private String storeAddress;
    private String properties;
}
