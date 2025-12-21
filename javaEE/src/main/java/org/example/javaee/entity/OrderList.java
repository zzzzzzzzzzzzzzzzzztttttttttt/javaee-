package org.example.javaee.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderList {
    private Integer nums;
    private String productName;
    private String productImage;
    private Double Price;
    private Date date;
}
