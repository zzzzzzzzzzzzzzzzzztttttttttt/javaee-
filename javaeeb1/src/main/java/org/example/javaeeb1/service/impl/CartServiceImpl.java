package org.example.javaeeb1.service.impl;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.mapper.CartMapper;
import org.example.javaeeb1.mapper.GoodsMapper;
import org.example.javaeeb1.pojo.Cart;
import org.example.javaeeb1.pojo.CartDto;
import org.example.javaeeb1.service.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:04
 */
@Service
public class CartServiceImpl  implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Override
    public Result addCart(Integer userId, Integer goodsId) {
        Cart cart=cartMapper.getCartByUserIdAndGoodsId(userId,goodsId);
        if(cart!=null){
            cartMapper.updateCart(cart.getCartId(),cart.getNum()+1,new Date(),cart.getTotalPrice().add(cart.getTotalPrice()));
            return Result.success();
        }
        cartMapper.addCart(userId,goodsId,new Date());
        return Result.success() ;
    }

    @Override
    public Result getAll(Integer userId) {
        List<CartDto> carts=cartMapper.getAll(userId);
        return Result.success(carts);
    }

    @Override
    public Result deleteCart(Integer cartId) {
        cartMapper.deleteCart(cartId);
        return Result.success();
    }

    @Override
    public Result updateCart(Integer cartId, Integer num, BigDecimal totalPrice) {
        cartMapper.updateCart(cartId,num,new Date(),totalPrice);
        return null;
    }
}
