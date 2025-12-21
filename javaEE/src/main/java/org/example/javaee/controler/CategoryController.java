package org.example.javaee.controler;

import org.example.javaee.common.Result;
import org.example.javaee.entity.Category;
import org.example.javaee.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/getAll")
    public Result getAll(){
        List<Category>c=categoryService.getAllCategories();
        return Result.success(c);
    }
}
