package org.example.javaee.controler;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.example.javaee.common.Result;
import org.example.javaee.entity.Cart;
import org.example.javaee.entity.User;
import org.example.javaee.entity.UserCart;
import org.example.javaee.service.CartService;
import org.example.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @PostMapping("/add/{username}/{productId}") // 路径模板：和前端拼接格式一致
    private Result addCart(
            // @PathVariable 绑定路径参数，required=true 表示必填（默认就是true）
            @PathVariable String username,
            @PathVariable Integer productId){
        System.out.println(username+" "+productId);
        boolean result=cartService.addCart(username,productId);
        if(result){
            return Result.success();
        }
        return Result.error("500","添加购物车失败");
    }
    @GetMapping("/getCart")
    private Result getCart(String username){
        System.out.println(username);
        User u=userService.getUserByUsername(username);
        List<UserCart> carts=cartService.findByUid(u.getId());
        return Result.success(carts);
    }
    @DeleteMapping("/delete/{id}")
    private Result deleteById(@PathVariable Integer id){
        boolean result=cartService.deleteById(id);
        if(result){
            return Result.success();
        }
        return Result.error("500","删除购物车失败");
    }
    @PutMapping("/update")
    private Result updateCart(@RequestBody UserCart usercart){
        System.out.println(usercart);
        boolean result=cartService.updateCart(usercart);
        if(result){
            return Result.success();
        }
        return Result.error("500","更新购物车失败");
    }

}
