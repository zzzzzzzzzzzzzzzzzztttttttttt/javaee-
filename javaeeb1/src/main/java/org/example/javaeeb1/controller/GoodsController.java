package org.example.javaeeb1.controller;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.Goods;
import org.example.javaeeb1.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:20
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    @RequestMapping("/getAll")
    public Result getAll(){
        List<Goods> g=goodsService.getAllGoods();
        System.out.println(g);
        return Result.success(g);
    }
}

