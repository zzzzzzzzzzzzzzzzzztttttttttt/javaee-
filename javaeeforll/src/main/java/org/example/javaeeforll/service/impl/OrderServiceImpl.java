package org.example.javaeeforll.service.impl;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Order;
import org.example.javaeeforll.mapper.OrderMapper;
import org.example.javaeeforll.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public int submitOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public Order getOrderByOrderId(String orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }
}