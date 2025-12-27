package org.example.javaeeb1.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartDto {
    private Integer cartId;
    private Integer userId;
    private String goodsName;
    private Integer num;
    private Date createTime;
    private BigDecimal totalPrice;
    private String goodsImg;
    private Integer goodsId;
}
