package org.example.javaeeforll.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.service.CartService;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/cart")
public class CartPageController {

    @Resource
    private CartService cartService;
    @Resource
    private GoodsService goodsService;

    // 展示购物车页面
    @GetMapping("/page")
    public String cartPage(HttpSession session, Model model) {
        // 实际项目中从会话获取登录用户ID
        Integer userId = 1; // 临时测试用
        model.addAttribute("cartList", cartService.getCartByUserId(userId));
        model.addAttribute("goodsService", goodsService); // 用于页面中查询商品信息
        return "front/cart";
    }
}