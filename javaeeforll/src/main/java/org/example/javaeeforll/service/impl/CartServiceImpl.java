package org.example.javaeeforll.service.impl;




import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.mapper.CartMapper;
import org.example.javaeeforll.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Override
    public int addCart(Cart cart) {
        return cartMapper.addCart(cart);
    }

    @Override
    public List<Cart> getCartByUserId(Integer userId) {
        return cartMapper.selectByUserId(userId);
    }

    @Override
    public int updateCartNum(Integer cartId, Integer num) {
        return cartMapper.updateNum(cartId, num);
    }

    @Override
    public int deleteCart(Integer cartId) {
        return cartMapper.deleteCart(cartId);
    }
}