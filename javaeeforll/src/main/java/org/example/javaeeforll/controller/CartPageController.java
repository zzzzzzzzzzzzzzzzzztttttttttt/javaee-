package org.example.javaeeforll.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.CartVO;
import org.example.javaeeforll.entity.User;
import org.example.javaeeforll.service.CartService;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


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
        User user = (User) session.getAttribute("user");
        List<CartDTO> cartList = cartService.getCartByUserId(user.getUserId());
        model.addAttribute("cartList", cartList );
        System.out.println(cartList);
        model.addAttribute("goodsService", goodsService); // 用于页面中查询商品信息
        return "front/cart";
    }
}