package com.telenor.assignment.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProductGetAPIDTO {
    private List<Product> data;
}
