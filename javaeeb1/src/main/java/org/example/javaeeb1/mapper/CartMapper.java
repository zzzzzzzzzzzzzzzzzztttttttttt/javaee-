package org.example.javaeeb1.mapper;

import org.example.javaeeb1.pojo.Cart;

import org.apache.ibatis.annotations.Mapper;
import org.example.javaeeb1.pojo.CartDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:04
 */
@Mapper
public interface CartMapper  {

    void addCart(Integer userId, Integer goodsId,Date createTime);

    List<CartDto> getAll(Integer userId);

    void deleteCart(Integer cartId);

    void updateCart(Integer cartId, Integer num, Date createTime, BigDecimal price);

    Cart getCartByUserIdAndGoodsId(Integer userId, Integer goodsId);
}
