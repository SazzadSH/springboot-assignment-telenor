package com.telenor.assignment.entity;

import com.telenor.assignment.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductGetDTO {
    private List<Product> data;
}
