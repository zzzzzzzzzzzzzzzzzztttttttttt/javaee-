package org.example.javaee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.javaee.entity.Cart;
import org.example.javaee.entity.UserCart;

import java.util.List;

@Mapper
public interface CartMapper {
    public void addCart(String username, Integer productId);

    List<UserCart> findById(Integer id);

    int deleteById(Integer id);

    int updateCart(Integer id,Integer nums);
}
