package org.example.javaeeb1.controller;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.service.CartService;
import org.example.javaeeb1.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:04
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;
    @Resource
    private UserService userService;
    @PostMapping("/add/{username}/{productId}")
    public Result addCart(@PathVariable String username, @PathVariable Integer productId) {

        Integer userId=userService.findByusername(username).getUserId();
        return cartService.addCart(userId,productId);
    }
    @GetMapping("/getAll/{username}")
    public Result getAll(@PathVariable String username) {
        System.out.println(username);
        Integer userId=userService.findByusername(username).getUserId();
        return cartService.getAll(userId);
    }
    @DeleteMapping("/delete/{cartId}")
    public Result deleteCart(@PathVariable  Integer cartId) {
        return cartService.deleteCart(cartId);
    }
    @PostMapping("/update")
    public Result updateCart(Integer cartId, Integer num, BigDecimal  price) {
        return cartService.updateCart(cartId,num,price);
    }
}

