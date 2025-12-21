package org.example.javaee.controler;

import org.example.javaee.common.Result;
import org.example.javaee.entity.*;
import org.example.javaee.service.CartService;
import org.example.javaee.service.OrderService;
import org.example.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public Result addOrder(@RequestBody OrderData orderData){
        Date date=new Date();
        String username=orderData.getUsername();
        User user=userService.getUserByUsername(username);
        Order o;
        for(UserCart cart:orderData.getCarts()){
            o=orderService.findByUserIdAndProductId(user.getId(),cart.getProductId());
            if(o==null){
                o=new Order();
                o.setOrderTime( date);
                o.setUserId(user.getId());
                o.setProductId(cart.getProductId());
                o.setCount(cart.getNums());
                o.setTotalPrice(cart.getProductPrice()*cart.getNums());
                orderService.addOrder(o);
            }else {
                System.out.println(o);
                o.setOrderTime(date);
                o.setCount(o.getCount()+cart.getNums());
                o.setTotalPrice(o.getTotalPrice()+cart.getProductPrice()*cart.getNums());
                orderService.updateOrder(o);
            }
            Double money=user.getMoney()-orderData.getTotalPrice();
            userService.updateMoney(username,money);
            cartService.deleteById(cart.getId());
        }
        return Result.success();
    }
    @GetMapping("/list")
    public Result getOrdersByUserId(String username){
        User user=userService.getUserByUsername(username);
        List<OrderList> orders=orderService.getOrdersLiastByUserId(user.getId());
        return Result.success(orders);
    }
}
