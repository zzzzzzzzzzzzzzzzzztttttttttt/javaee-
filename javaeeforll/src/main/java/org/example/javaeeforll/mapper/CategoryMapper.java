package org.example.javaeeforll.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.javaeeforll.entity.Category;

import java.util.List;
@Mapper
public interface CategoryMapper {
    // 查询所有分类
    @Select("select * from t_category order by sort asc")
    List<Category> selectAll();

    // 根据父分类ID查询子分类
    @Select("select * from t_category where parent_id=#{parentId}")
    List<Category> selectByParentId(Integer parentId);
}