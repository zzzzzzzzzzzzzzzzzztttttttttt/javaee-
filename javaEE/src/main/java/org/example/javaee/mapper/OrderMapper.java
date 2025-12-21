package org.example.javaee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.javaee.entity.Order;
import org.example.javaee.entity.OrderList;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order selectById(Integer id);
    // 根据用户ID查询订单
    List<Order> selectByUserId(Integer userId);
    List<Order> selectAll();
    int insert(Order order);
    int update(Order order);
    int deleteById(Integer id);

    Order findByUserIdAndProductId(Integer id, Integer productId);

    List<OrderList> getOrdersLiastByUserId(Integer id);
}