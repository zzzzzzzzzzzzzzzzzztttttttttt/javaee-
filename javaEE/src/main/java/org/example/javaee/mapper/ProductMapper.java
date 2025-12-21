package org.example.javaee.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.javaee.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product selectById(Integer id);
    List<Product> selectAll();
    // 根据分类ID查询商品
    List<Product> selectByCategoryId(Integer categoryId);
    int insert(Product product);
    int update(Product product);
    int deleteById(Integer id);
}