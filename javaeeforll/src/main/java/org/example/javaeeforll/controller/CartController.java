package org.example.javaeeforll.controller;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartAddRequest;
import org.example.javaeeforll.service.CartService;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    // 加入购物车
    @PostMapping("/add")
// 注意：如果返回的是字符串，建议添加@ResponseBody（如果类上有@RestController则无需）
    public String addCart(@RequestBody CartAddRequest request) {
        Cart cart = new Cart();
        cart.setUserId(request.getUserId());
        cart.setGoodsId(request.getGoodsId());
        cart.setNum(request.getNum()); // 前端不传num时，使用默认值1
        cart.setCreateTime(new Date());
        System.out.println(cart);
        int result = cartService.addCart(cart);
        return result > 0 ? "success" : "fail";
    }
    // 查询购物车
    @GetMapping("/list/{userId}")
    public List<Cart> getCartList(@PathVariable Integer userId) {
        return cartService.getCartByUserId(userId);
    }

    // 修改数量
    @PutMapping("/update")
    public String updateNum(Integer cartId, Integer num) {
        int result = cartService.updateCartNum(cartId, num);
        return result > 0 ? "success" : "fail";
    }

    // 删除商品
    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable Integer cartId) {
        return cartService.deleteCart(cartId) > 0 ? "success" : "fail";
    }
}