package org.example.javaeeforll.service.impl;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.CartDTO;
import org.example.javaeeforll.entity.Order;
import org.example.javaeeforll.entity.OrderSubmitDTO;
import org.example.javaeeforll.mapper.OrderMapper;
import org.example.javaeeforll.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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
    public List<OrderSubmitDTO> getOrderByUserId(Integer userId) {
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

    public String createOrder(CartDTO cartDTO, Integer userId) {
        Integer goodsId = cartDTO.getGoodsId();

        Order o = orderMapper.selectByUserIdAndGoodsId(goodsId, userId);
        if(o== null){
            Order order = new Order();
            order.setNum(cartDTO.getNum());
            order.setGoodsId(goodsId);
            order.setUserId(userId);
            order.setTotalPrice(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getNum())));
            order.setCreateTime(new Date());
            orderMapper.insertOrder(order);
        }
        else {
            o.setNum(o.getNum()+cartDTO.getNum());
            o.setTotalPrice(o.getTotalPrice().add(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getNum()))));
            o.setCreateTime(new Date());
            System.out.println(o);
            orderMapper.updateOrder(o);
        }
        return "";
    }
}