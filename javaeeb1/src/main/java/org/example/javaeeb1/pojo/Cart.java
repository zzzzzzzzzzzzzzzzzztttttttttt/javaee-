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
 * @since 2025-12-26 08:03:04
 */
@Data
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cartId;

    private Integer userId;
    private Integer goodsId;
    private Integer num;
    private Date createTime;
    private BigDecimal totalPrice;


}
