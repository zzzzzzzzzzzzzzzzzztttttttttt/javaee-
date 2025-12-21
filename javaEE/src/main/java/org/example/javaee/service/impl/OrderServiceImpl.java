package org.example.javaee.service.impl;


import org.example.javaee.entity.Order;
import org.example.javaee.entity.OrderList;
import org.example.javaee.mapper.OrderMapper;
import org.example.javaee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOrderById(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    @Override
    public boolean addOrder(Order order) {
        return orderMapper.insert(order) > 0;
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderMapper.update(order) > 0;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        return orderMapper.deleteById(id) > 0;
    }

    @Override
    public Order findByUserIdAndProductId(Integer id, Integer productId){
        return orderMapper.findByUserIdAndProductId(id,productId);
    }
    @Override
    public List<OrderList> getOrdersLiastByUserId(Integer id){
        return orderMapper.getOrdersLiastByUserId(id);
    }
}