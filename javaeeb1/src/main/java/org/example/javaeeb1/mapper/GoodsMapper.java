package org.example.javaeeb1.mapper;

import org.example.javaeeb1.pojo.Goods;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:20
 */
@Mapper
public interface GoodsMapper  {

    List<Goods> getAllGoods();
}
