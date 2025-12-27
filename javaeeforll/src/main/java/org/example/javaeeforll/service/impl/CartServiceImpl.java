package org.example.javaeeforll.service.impl;




import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.CartVO;
import org.example.javaeeforll.entity.Goods;
import org.example.javaeeforll.mapper.CartMapper;
import org.example.javaeeforll.service.CartService;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsService goodsService;

    @Override
    public void addCart(Cart cart) {
        cartMapper.addCart(cart);
    }

    @Override
    public List<CartDTO> getCartByUserId(Integer userId) {
        return cartMapper.selectCartByUserId(userId);
    }

    @Override
    public int updateCartNum(Integer cartId, Integer num) {
        return cartMapper.updateNum(cartId, num);
    }

    @Override
    public int deleteCart(Integer cartId) {
        return cartMapper.deleteCart(cartId);
    }
    public List<CartVO> getCartVOByUserId(Integer userId) {
        // 1. 获取原始购物车列表
        List<CartDTO> cartList = getCartByUserId(userId);

        // 2. 转换为VO并填充商品信息
        return cartList.stream().map(cart -> {
            CartVO cartVO = new CartVO();
            // 拷贝购物车基础字段
            BeanUtils.copyProperties(cart, cartVO);

            // 3. 安全获取商品信息，避免空指针
            Goods goods = goodsService.getGoodsById(cart.getGoodsId());
            if (goods != null) {
                cartVO.setGoodsName(goods.getGoodsName());
                cartVO.setPrice(goods.getPrice());
                cartVO.setGoodsExist(true);
            } else {
                // 商品不存在时的默认值
                cartVO.setGoodsName("【商品已下架/删除】");
                cartVO.setPrice(BigDecimal.ZERO);
                cartVO.setGoodsExist(false);
            }
            return cartVO;
        }).collect(Collectors.toList());
    }
}