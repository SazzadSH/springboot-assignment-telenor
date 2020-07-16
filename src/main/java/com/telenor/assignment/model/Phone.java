package com.telenor.assignment.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("phone")
public class Phone extends Product {
    @Column(name = "color")
    private String color;

    @Override
    public String getType() {
        return "phone";
    }

    @Override
    public String getProperties() {
        return new StringBuffer().append("color:").append(getColor()).toString();
    }
}
