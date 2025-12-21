package org.example.javaee.service;

import org.example.javaee.entity.Order;
import org.example.javaee.entity.OrderList;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer id);
    List<Order> getOrdersByUserId(Integer userId);
    List<Order> getAllOrders();
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(Integer id);

    Order findByUserIdAndProductId(Integer id, Integer productId);

    List<OrderList> getOrdersLiastByUserId(Integer id);
}