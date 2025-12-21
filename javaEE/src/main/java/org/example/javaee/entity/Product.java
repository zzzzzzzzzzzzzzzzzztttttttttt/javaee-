package org.example.javaee.entity;

import lombok.Data;



@Data
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private Integer categoryId;
    private String img;
}
