package org.example.javaee.controler;

import org.example.javaee.common.Result;
import org.example.javaee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/getAll")
    public Result getAll(){
        return Result.success(productService.getAllProducts());
    }
    @GetMapping("/getByCategory/{id}")
    public Result getByCategoryId(@PathVariable Integer id){
        return Result.success(productService.getProductsByCategoryId(id));
    }
}
