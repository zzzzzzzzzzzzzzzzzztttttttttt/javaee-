package org.example.javaeeb1.service;

import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.Cart;

import java.math.BigDecimal;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:04
 */
public interface CartService {

    Result addCart(Integer userId, Integer goodsId);

    Result getAll(Integer userId);

    Result deleteCart(Integer cartId);

    Result updateCart(Integer cartId, Integer num, BigDecimal  price);
}
