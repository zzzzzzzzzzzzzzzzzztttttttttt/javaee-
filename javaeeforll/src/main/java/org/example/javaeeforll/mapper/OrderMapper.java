package org.example.javaeeforll.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.javaeeforll.entity.Order;
import org.example.javaeeforll.entity.OrderSubmitDTO;

import java.util.List;
@Mapper
public interface OrderMapper {
    // 提交订单
    int insertOrder(Order order);

    // 查询用户订单
    List<OrderSubmitDTO> selectByUserId(Integer userId);

    // 查询订单详情
    Order selectByOrderId(String orderId);

    // 查询所有订单（后台）
    List<Order> selectAll();

    Order selectByUserIdAndGoodsId(Integer goodsId, Integer userId);

    void updateOrder(Order o);
}