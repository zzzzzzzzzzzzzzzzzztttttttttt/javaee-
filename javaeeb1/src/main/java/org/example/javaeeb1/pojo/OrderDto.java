package org.example.javaeeb1.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private Integer orderId;
    private Integer userId;
    private String createTime;
    private BigDecimal totalPrice;
    private Integer num;
    private String goodsName;
    private String goodsImg;

}
