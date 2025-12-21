package org.example.javaee.service;


import org.example.javaee.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(Integer categoryId);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Integer id);
}