package org.example.javaee.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer count;
    private Double totalPrice;
    private Date orderTime;
}
