package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.Order;

import java.util.List;

public interface OrderService {
    int submitOrder(Order order);
    List<Order> getOrderByUserId(Integer userId);
    Order getOrderByOrderId(String orderId);
    List<Order> getAllOrders();
}