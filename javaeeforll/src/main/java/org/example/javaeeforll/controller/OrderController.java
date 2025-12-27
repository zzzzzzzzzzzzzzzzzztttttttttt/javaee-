package org.example.javaeeforll.controller;

import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.OrderSubmitDTO;
import org.example.javaeeforll.entity.User;
import org.example.javaeeforll.service.CartService;
import org.example.javaeeforll.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 订单页面控制器
 */
@Controller
@RequestMapping("/front") // 根路径匹配/front
public class OrderController {

    @Autowired
    private CartService cartService; // 注入你的购物车服务
    @Autowired
    private OrderService orderService;
    /**
     * 跳转到订单提交页面
     */
    @GetMapping("/checkout")
    public String toCheckout(HttpSession session, Model model) {
        // 1. 校验用户是否登录
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录跳转到登录页（根据你的实际登录页路径修改）
            return "redirect:/front/login";
        }

        // 2. 获取用户购物车数据
        List<CartDTO> cartList = cartService.getCartByUserId(user.getUserId());
        for (CartDTO cart : cartList){
            orderService.createOrder(cart,user.getUserId());
            cartService.deleteCart(cart.getCartId());
        }
        // 3. 传递数据到页面
        List<OrderSubmitDTO> orderList = orderService.getOrderByUserId(user.getUserId());
        model.addAttribute("orderList", orderList);
        System.out.println(orderList);
        model.addAttribute("user", user);
        model.addAttribute("cartList", cartList);

        // 4. 返回模板路径（对应templates/front/checkout.html）
        return null;
    }
    @GetMapping("/index")
    public String toIndex() {
        // 返回首页模板路径（对应templates/front/index.html）
        return "front/index";
    }

}