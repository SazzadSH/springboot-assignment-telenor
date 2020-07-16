package com.telenor.assignment.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.STRING,
        name = "type"
)
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Product extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Column(name = "type")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType type;*/

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "store_address")
    private String storeAddress;

    /*@Column(name = "properties")
    private String properties;*/

    public abstract String getType();

    public abstract String getProperties();
}
