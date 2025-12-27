package org.example.javaeeb1.controller;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:40
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("/getAll/{nowUser}")
    public Result getAll(@PathVariable String nowUser){
        return orderService.getAll(nowUser);
    }
    @GetMapping("/add/{nowUser}")
    public Result add(@PathVariable String nowUser){
        return orderService.add(nowUser);
    }
}

