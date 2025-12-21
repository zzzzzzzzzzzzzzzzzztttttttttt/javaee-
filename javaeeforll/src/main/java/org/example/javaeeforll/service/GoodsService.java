package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getGoodsByCategoryId(Integer categoryId);
    Goods getGoodsById(Integer goodsId);
    int addGoods(Goods goods);
    List<Goods> getAllGoods();
}