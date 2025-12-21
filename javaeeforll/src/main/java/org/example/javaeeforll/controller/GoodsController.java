package org.example.javaeeforll.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.javaeeforll.service.CategoryService;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private CategoryService categoryService;

    // 按分类查询商品
    @GetMapping("/category/{categoryId}")
    public String getGoodsByCategory(@PathVariable Integer categoryId, HttpSession session) {
        if(categoryId == 0){
            session.setAttribute("goodsList", goodsService.getAllGoods());
            return "front/goodsList";
        }
        session.setAttribute("goodsList", goodsService.getGoodsByCategoryId(categoryId));
        session.setAttribute("categoryList", categoryService.getAllCategory());
        System.out.println(goodsService.getGoodsByCategoryId(categoryId));
        return "front/goodsList";
    }

    // 商品详情
    @GetMapping("/detail/{goodsId}")
    public String goodsDetail(@PathVariable Integer goodsId, Model model) {
        model.addAttribute("goods", goodsService.getGoodsById(goodsId));
        return "front/goodsDetail";
    }
}