package org.example.javaeeb1.controller;


import jakarta.annotation.Resource;
import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.Category;
import org.example.javaeeb1.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:02:53
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("/getAll")
    public Result getAll(){
        List<Category> c=categoryService.getAllCategories();
        System.out.println(c);
        return Result.success(c);
    }
}

