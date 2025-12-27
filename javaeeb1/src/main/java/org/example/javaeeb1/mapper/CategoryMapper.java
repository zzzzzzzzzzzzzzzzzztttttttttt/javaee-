package org.example.javaeeb1.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.javaeeb1.pojo.Category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:02:53
 */
@Mapper
public interface CategoryMapper  {
    List<Category> getAllCategories();
}
