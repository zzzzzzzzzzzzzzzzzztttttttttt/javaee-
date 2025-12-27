package org.example.javaeeforll.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.entity.User;
import org.example.javaeeforll.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Date;

@Controller
@RequestMapping("/user")
public class FrontUserController {

    @Resource
    private UserService userService;

    // 跳转注册页
    @GetMapping("/toRegister")
    public String toRegister() {
        return "front/register";
    }

    // 用户注册
    @PostMapping("/register")
    public String register(User user, Model model) {
        user.setStatus(1);
        user.setCreateTime(new Date());
        int result = userService.register(user);
        if (result > 0) {
            return "redirect:/user/toLogin";
        } else {
            model.addAttribute("msg", "注册失败，请重试");
            return "front/register";
        }
    }

    // 跳转登录页
    @GetMapping("/toLogin")
    public String toLogin() {
        return "front/login";
    }@GetMapping("/logout")
    public String toLogout() {
        return "front/login";
    }

    // 用户登录
    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);

        User user = userService.login(username, password);
        System.out.println(user);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("msg", "账号或密码错误");
            return "front/login";
        }
    }
}