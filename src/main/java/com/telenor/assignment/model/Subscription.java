package com.telenor.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

import static com.telenor.assignment.util.Constants.SUBSCRIPTION_TABLE;

@Data
@Entity
@DiscriminatorValue(SUBSCRIPTION_TABLE)
public class Subscription extends Product {
    @JsonIgnore
    @Column(name = "gb_limit")
    private Integer gbLimit;

    @Override
    public String getType() {
        return SUBSCRIPTION_TABLE;
    }

    @Override
    public String getProperties() {
        return "gb_limit:" + getGbLimit().toString();
    }


}
