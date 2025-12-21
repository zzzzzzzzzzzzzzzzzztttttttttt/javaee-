package org.example.javaeeforll.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.javaeeforll.entity.Goods;

import java.util.List;
@Mapper
public interface GoodsMapper {
    // 根据分类ID查询商品
    List<Goods> selectByCategoryId(Integer categoryId);

    // 根据商品ID查询详情
    Goods selectById(Integer goodsId);

    // 新增商品
    int insertGoods(Goods goods);

    // 查询所有商品
    List<Goods> selectAll();
}