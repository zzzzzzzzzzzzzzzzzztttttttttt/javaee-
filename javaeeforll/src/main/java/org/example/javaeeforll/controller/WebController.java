package org.example.javaeeforll.controller;

import jakarta.annotation.Resource;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/")
    public String index(Model model) {
        // 查询推荐商品（这里取前8个商品作为推荐，可根据业务调整）
        model.addAttribute("recommendGoods", goodsService.getAllGoods());
        return "front/index";
    }
}