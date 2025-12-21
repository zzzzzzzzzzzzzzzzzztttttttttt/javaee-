package org.example.javaee.entity;

import lombok.Data;

@Data
public class Cart {
    private Integer id;
    private Integer userId;
    private  Integer productId;
    private Integer nums;
}
