package org.example.javaeeb1.mapper;

import org.example.javaeeb1.pojo.Order;

import org.apache.ibatis.annotations.Mapper;
import org.example.javaeeb1.pojo.OrderDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:40
 */
@Mapper
public interface OrderMapper  {

    List<OrderDto> getAll(Integer userId);

    Order findByUserIdAndGoodsId(Integer userId, Integer goodsId);

    void add(Integer userId, Integer goodsId, Integer num, BigDecimal totalPrice, Date createTime);

    void update(Integer orderId, Integer num, BigDecimal totalPrice, Date date);
}
