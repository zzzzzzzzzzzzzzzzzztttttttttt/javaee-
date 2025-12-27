package org.example.javaee.controler;

import lombok.extern.slf4j.Slf4j;
import org.example.javaee.common.Result;
import org.example.javaee.entity.User;
import org.example.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String username =user.getUsername();
        String password=user.getPassword();
        User u1=userService.getUserByUsername(username);
        if (u1==null){
            return Result.error("500","用户不存在");
        }
        if(u1.getUsername().equals(username)&&u1.getPassword().equals(password)){
            return Result.success();
        }
        return Result.error("500","用户名或密码错误");
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("Aaaa");
        String username =user.getUsername();
        User u=userService.getUserByUsername(username);
        if(u!=null){
            return Result.error("500","该用户已存在");
        }
        user.setMoney(0.0);
        userService.addUser(user);

        return Result.success();
    }
}
