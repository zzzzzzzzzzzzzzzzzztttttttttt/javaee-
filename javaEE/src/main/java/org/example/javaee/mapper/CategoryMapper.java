package org.example.javaee.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.javaee.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Category selectById(Integer id);
    List<Category> selectAll();
    int insert(Category category);
    int update(Category category);
    int deleteById(Integer id);
}