package org.example.javaeeb1.service.impl;

import jakarta.annotation.Resource;
import org.example.javaeeb1.pojo.Goods;
import org.example.javaeeb1.mapper.GoodsMapper;
import org.example.javaeeb1.service.GoodsService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:20
 */
@Service
public class GoodsServiceImpl  implements GoodsService {
@Resource
private GoodsMapper goodsMapper;
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }
}
