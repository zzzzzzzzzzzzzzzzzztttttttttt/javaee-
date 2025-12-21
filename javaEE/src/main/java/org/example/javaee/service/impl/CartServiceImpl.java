package org.example.javaee.service.impl;

import org.example.javaee.entity.Cart;
import org.example.javaee.entity.UserCart;
import org.example.javaee.mapper.CartMapper;
import org.example.javaee.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Override
    public boolean addCart( String username,Integer productId) {

        cartMapper.addCart(username,productId);
        return true;
    }
    @Override
    public List<UserCart> findByUid(Integer userId) {
        return cartMapper.findById(userId);
    }
    @Override
    public boolean deleteById(Integer id){
        return cartMapper.deleteById(id)>0;
    }
    @Override
    public boolean updateCart(UserCart usercart){
        return cartMapper.updateCart(usercart.getId(),usercart.getNums())>0;
    }

}
