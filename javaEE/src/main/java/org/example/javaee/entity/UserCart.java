package org.example.javaee.entity;

import lombok.Data;

@Data
public class UserCart {
    private Integer id;
    private Integer nums;
    private Integer userId;
    private Integer productId;
    private String userName;
    private String productName;
    private String productImage;
    private Double productPrice;
}
