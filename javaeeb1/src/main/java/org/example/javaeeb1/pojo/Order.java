package org.example.javaeeb1.pojo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:40
 */
@Data
public class Order implements Serializable {


    private Integer orderId;
    private Integer userId;

    private BigDecimal totalPrice;
    private Date createTime;
    private Integer num;
    private Integer goodsId;


}
