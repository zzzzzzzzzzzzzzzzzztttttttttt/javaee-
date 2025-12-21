package org.example.javaee.service;

import org.example.javaee.entity.Cart;
import org.example.javaee.entity.User;
import org.example.javaee.entity.UserCart;

import java.util.List;

public interface CartService {
    boolean addCart(String username, Integer productId);
    List<UserCart> findByUid(Integer userId);

    boolean deleteById(Integer id);

    boolean updateCart(UserCart usercart);
}
