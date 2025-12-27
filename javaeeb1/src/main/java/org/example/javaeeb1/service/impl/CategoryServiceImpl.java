package org.example.javaeeb1.service.impl;

import jakarta.annotation.Resource;
import org.example.javaeeb1.pojo.Category;
import org.example.javaeeb1.mapper.CategoryMapper;
import org.example.javaeeb1.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:02:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
}
