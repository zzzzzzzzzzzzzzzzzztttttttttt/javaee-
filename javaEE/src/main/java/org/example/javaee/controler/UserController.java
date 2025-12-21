package org.example.javaee.controler;


import org.example.javaee.common.Result;
import org.example.javaee.entity.User;
import org.example.javaee.entity.UserMoney;
import org.example.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 根据ID查询用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // 查询所有用户
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 新增用户
    @PostMapping
    public ResponseEntity<Boolean> addUser(@RequestBody User user) {
        boolean success = userService.addUser(user);
        return ResponseEntity.ok(success);
    }

    // 更新用户
    @PutMapping
    public ResponseEntity<Boolean> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        return ResponseEntity.ok(success);
    }
    @GetMapping("/getByUserName")
    public Result getUserByUsername(@RequestParam(value = "username", required = true) String username) {
        System.out.println(username);
        User user = userService.getUserByUsername(username);
        System.out.println(user);
        if (user != null) {
            return Result.success(user.getMoney());
        } else {
            return Result.error("USER_NOT_FOUND", "用户不存在");
        }
    }
    @PostMapping("/updateMoney")
    public Result updateMoney(@RequestBody UserMoney userMoney) {
        System.out.println(userMoney);
        boolean result = userService.updateMoney(userMoney.getUsername(), userMoney.getMoney());
        if (result) {
            return Result.success();
        } else {
            return Result.error("500", "更新用户余额失败");
        }
    }
    @Transactional
    @PostMapping("/recharge")
    public Result recharge(@RequestBody UserMoney userMoney) {
        System.out.println(userMoney);
        boolean result = userService.updateMoney(userMoney.getUsername(), userMoney.getMoney());
        if (result) {
            return Result.success();
        } else {
            return Result.error("500", "更新用户余额失败");
        }
    }
}