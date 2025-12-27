package org.example.javaeeforll.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 购物车视图对象，封装购物车和商品的组合信息
 */
@Data
public class CartVO {
    // 购物车原有字段
    private Long cartId;
    private Long userId;
    private Long goodsId;
    private Integer num;
    
    // 商品关联字段
    private String goodsName;  // 商品名称
    private BigDecimal price;  // 商品单价
    private Boolean goodsExist; // 标记商品是否存在
}