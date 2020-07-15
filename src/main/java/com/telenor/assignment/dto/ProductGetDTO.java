package com.telenor.assignment.dto;

import com.telenor.assignment.model.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductGetDTO {
    private List<Product> data;
}
