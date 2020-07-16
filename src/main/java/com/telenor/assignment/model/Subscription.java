package com.telenor.assignment.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@DiscriminatorValue("subscription")
public class Subscription extends Product {
    @Column(name = "gb_limit")
    private BigDecimal gbLimit;

    @Override
    public String getType() {
        return "subscription";
    }

    @Override
    public String getProperties() {
        return new StringBuffer().append("gb_limit:").append(getGbLimit()).toString();
    }


}
