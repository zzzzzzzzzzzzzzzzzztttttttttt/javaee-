package org.example.javaee.service;


import org.example.javaee.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Integer id);
    List<Category> getAllCategories();
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(Integer id);
}