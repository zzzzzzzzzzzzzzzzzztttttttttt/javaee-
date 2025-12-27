package org.example.javaeeforll.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDTO {
        private Integer cartId;
        private Integer userId;
        private Integer goodsId;
        private Integer num;
        private String goodsName;
        private BigDecimal price;
        private String goodsImg;
        // getter/setter
    }