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
 * @since 2025-12-26 08:03:20
 */
@Data

public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer goodsId;

    private String goodsName;
    private Integer categoryId;
    private BigDecimal price;
    private Integer stock;
    private String goodsDesc;
    private String goodsImg;
    private Date createTime;


}
