package org.example.javaeeforll.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Cart {
    private Integer cartId;
    private Integer userId;
    private Integer goodsId;
    private Integer num;
    private Date createTime;
}