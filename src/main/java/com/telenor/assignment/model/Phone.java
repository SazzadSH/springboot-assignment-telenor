package com.telenor.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.telenor.assignment.util.Constants.PHONE_TABLE;

@Data
@Entity
@DiscriminatorValue(PHONE_TABLE)
public class Phone extends Product {
    @JsonIgnore
    @Column(name = "color")
    private String color;

    @Override
    public String getType() {
        return PHONE_TABLE;
    }

    @Override
    public String getProperties() {
        return new StringBuffer().append("color:").append(getColor()).toString();
    }
}
