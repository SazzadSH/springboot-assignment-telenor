package com.telenor.assignment.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.telenor.assignment.dto.helper.Property;
import com.telenor.assignment.dto.helper.PropertyDetails;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductGetFiltersDTO {
    @ApiParam(value = "The product type. (String. Can be 'phone' or 'subscription')")
    private String type;

    @ApiParam(value = "The minimum price in SEK. (Number)", defaultValue = "0.0")
    private BigDecimal minPrice;

    @ApiParam(value = "The maximum price in SEK. (Number)", defaultValue = "0.0")
    private BigDecimal maxPrice;

    @ApiParam(value = "The city in which a store is located. (String)")
    private String city;

    @ApiParam(value = "Property values")
    private PropertyDetails propertyDetails;
}
