package org.example.Mappers;

import org.example.pojo.Orders;

import java.util.List;

public interface OrdersMapper {
    List<Orders> findOrdersByUserId(int id);
}
