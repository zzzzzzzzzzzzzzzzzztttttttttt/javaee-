package org.example.javaeeforll.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private String orderId;
    private Integer userId;
    private BigDecimal totalPrice;
    private String receiver;
    private String phone;
    private String address;
    private Integer orderStatus; // 0-待付款 1-已付款 2-已发货 3-已完成
    private Date createTime;
}