package org.example.javaeeforll.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.entity.Cart;
import org.example.javaeeforll.entity.CartAddRequest;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.User;
import org.example.javaeeforll.service.CartService;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    // 加入购物车
// SpringBoot示例
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addCart(@RequestBody CartDTO cartDTO ,HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = (User) session.getAttribute("user");
            System.out.println("接收到的数据：" + cartDTO);
            Cart c=new Cart();
            c.setUserId(user.getUserId());
            c.setGoodsId(cartDTO.getGoodsId());
            c.setNum(cartDTO.getNum());
            c.setCreateTime(new Date());
            // 业务逻辑：检查库存、添加购物车
            System.out.println("添加购物车：" + c);
            cartService.addCart(c);
            result.put("success", true);
            result.put("msg", "加入购物车成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "加入失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result; // 始终返回JSON对象
    }

    // 对应的DTO类

    // 查询购物车
    @GetMapping("/list/{userId}")
    public List<CartDTO> getCartList(@PathVariable Integer userId) {
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