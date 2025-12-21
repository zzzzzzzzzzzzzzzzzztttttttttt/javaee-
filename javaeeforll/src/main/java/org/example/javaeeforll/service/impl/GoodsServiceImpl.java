package org.example.javaeeforll.service.impl;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Goods;
import org.example.javaeeforll.mapper.GoodsMapper;
import org.example.javaeeforll.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsByCategoryId(Integer categoryId) {
        return goodsMapper.selectByCategoryId(categoryId);
    }

    @Override
    public Goods getGoodsById(Integer goodsId) {
        return goodsMapper.selectById(goodsId);
    }

    @Override
    public int addGoods(Goods goods) {
        return goodsMapper.insertGoods(goods);
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.selectAll();
    }
}