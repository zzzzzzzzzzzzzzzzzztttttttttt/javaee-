package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.Order;
import org.example.javaeeforll.entity.OrderSubmitDTO;

import java.util.List;

public interface OrderService {
    int submitOrder(Order order);
    List<OrderSubmitDTO> getOrderByUserId(Integer userId);
    Order getOrderByOrderId(String orderId);
    List<Order> getAllOrders();

    String createOrder(CartDTO cartDTO, Integer userId);
}