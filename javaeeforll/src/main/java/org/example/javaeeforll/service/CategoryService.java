package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    List<Category> getSubCategory(Integer parentId);
}