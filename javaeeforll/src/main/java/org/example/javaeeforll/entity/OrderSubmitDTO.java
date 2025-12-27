package org.example.javaeeforll.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderSubmitDTO {
    private Integer nums; // 用户ID
    private BigDecimal totalAmount; // 订单总金额
    private String orderImg;
    private String orderName;
    private Date date;

}