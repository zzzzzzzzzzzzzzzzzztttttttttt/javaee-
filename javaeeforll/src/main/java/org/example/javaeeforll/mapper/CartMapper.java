package org.example.javaeeforll.mapper;


import org.apache.ibatis.annotations.*;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartDTO;

import java.util.List;
@Mapper
public interface CartMapper {
    // 加入购物车

    int addCart(Cart cart);

    // 查询用户购物车

    List<CartDTO> selectCartByUserId(Integer userId);

    // 修改购物车数量
    @Update("update t_cart set num=#{num} where cart_id=#{cartId}")
    int updateNum(@Param("cartId") Integer cartId, @Param("num") Integer num);

    // 删除购物车商品
    @Delete("delete from t_cart where cart_id=#{cartId}")
    int deleteCart(Integer cartId);
}