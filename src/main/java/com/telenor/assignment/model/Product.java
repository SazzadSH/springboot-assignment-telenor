package com.telenor.assignment.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "properties")
    private String properties;
}
