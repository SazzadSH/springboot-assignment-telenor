package com.telenor.assignment.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.telenor.assignment.helper.ProductTypeConverter;
import com.telenor.assignment.model.helper.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "products")
@JsonAutoDetect
public class Product extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType type;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "properties")
    private String properties;
}
