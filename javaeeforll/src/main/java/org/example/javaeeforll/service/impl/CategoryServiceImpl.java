package org.example.javaeeforll.service.impl;

import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Category;
import org.example.javaeeforll.mapper.CategoryMapper;
import org.example.javaeeforll.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> getSubCategory(Integer parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
}