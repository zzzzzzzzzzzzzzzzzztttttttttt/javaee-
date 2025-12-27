package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.CartVO;

import java.util.List;

public interface CartService {
    void addCart(Cart cart);
    List<CartDTO> getCartByUserId(Integer userId);
    int updateCartNum(Integer cartId, Integer num);
    int deleteCart(Integer cartId);
    public List<CartVO> getCartVOByUserId(Integer userId);

}