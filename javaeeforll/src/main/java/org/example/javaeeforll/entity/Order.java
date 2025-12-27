package org.example.javaeeforll.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private String orderId;
    private Integer userId;
    private BigDecimal totalPrice;
    private Date createTime;
    private Integer num;
    private Integer goodsId;
}