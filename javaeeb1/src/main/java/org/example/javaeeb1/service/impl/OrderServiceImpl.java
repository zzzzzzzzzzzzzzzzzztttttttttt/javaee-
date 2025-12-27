package org.example.javaeeb1.service.impl;

import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.Cart;
import org.example.javaeeb1.pojo.CartDto;
import org.example.javaeeb1.pojo.Order;
import org.example.javaeeb1.mapper.OrderMapper;
import org.example.javaeeb1.pojo.User;
import org.example.javaeeb1.service.CartService;
import org.example.javaeeb1.service.OrderService;
import org.example.javaeeb1.service.UserService;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:40
 */
@Service
public class OrderServiceImpl implements OrderService {
@Resource
private OrderMapper orderMapper;
@Resource
private UserService userService;
@Resource
private CartService cartService;
    @Override
    public Result getAll(String nowUser) {
        Integer userId=userService.findByusername(nowUser).getUserId();
        return Result.success(orderMapper.getAll(userId));
    }

    @Override
    public Result add(String nowUser) {
        User user=userService.findByusername(nowUser);
        Integer userId=user.getUserId();
        Result carts=cartService.getAll(userId);
        if(carts.getData()!=null){
            List<CartDto> cartList=(List<CartDto>) carts.getData();
            for(CartDto cart:cartList){
                Order o=orderMapper.findByUserIdAndGoodsId(userId,cart.getGoodsId());
                System.out.println(o);
                if(o==null){
                    orderMapper.add(userId,cart.getGoodsId(),cart.getNum(),cart.getTotalPrice(),new Date());
                    System.out.println("ADSASDASD");
                }else{
                    orderMapper.update(o.getOrderId(),cart.getNum()+o.getNum(),cart.getTotalPrice().add(o.getTotalPrice()),new Date());
                }
                cartService.deleteCart(cart.getCartId());
            }
        }
        return Result.success();

    }
}
