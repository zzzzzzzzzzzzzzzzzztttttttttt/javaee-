package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.Cart;

import java.util.List;

public interface CartService {
    int addCart(Cart cart);
    List<Cart> getCartByUserId(Integer userId);
    int updateCartNum(Integer cartId, Integer num);
    int deleteCart(Integer cartId);
}