package org.example.javaeeb1.controller;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.User;
import org.example.javaeeb1.pojo.UserDto;
import org.example.javaeeb1.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;




/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:04:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User u =userService.login(user.getUsername());
        if (u==null){
            return Result.error("500","用户不存在");
        }
        if (!u.getPassword().equals(user.getPassword())){
            return Result.error("500","密码错误");
        }
        return Result.success(u.getMoney());
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        if(!userDto.getPassword().equals(userDto.getCpassword())){
            return Result.error("500","密码不一致");
        }
        Boolean u = userService.register(userDto);
        return u==null?Result.error("500","注册失败"):Result.success();
    }
}

