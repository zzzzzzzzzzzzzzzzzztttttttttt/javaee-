package org.example.javaeeforll.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Goods {
    private Integer goodsId;
    private String goodsName;
    private Integer categoryId;
    private BigDecimal price;
    private Integer stock;
    private String goodsDesc;
    private String goodsImg;
    private Date createTime;
}