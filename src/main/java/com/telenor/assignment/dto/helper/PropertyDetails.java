package com.telenor.assignment.dto.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class PropertyDetails {

    @ApiParam(value = "The name of the property. (String. Can be 'color' or 'gb_limit')")
    private Property property;

    @JsonProperty("color")
    @ApiParam(value = "The color of the phone. (String)")
    private String color;

    @JsonProperty("gb_limit_min")
    @ApiParam(value = "The minimum GB limit of the subscription. (Number)", defaultValue = "0.0")
    private BigDecimal gbLimitMin;

    @JsonProperty("gb_limit_max")
    @ApiParam(value = "The maximum GB limit of the subscription. (Number)", defaultValue = "0.0")
    private BigDecimal gbLimitMax;
}
