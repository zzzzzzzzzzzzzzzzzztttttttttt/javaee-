package org.example.javaeeforll.controller;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.Order;
import org.example.javaeeforll.service.OrderService;
import org.example.javaeeforll.util.OrderNumUtil;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    // 提交订单
    @PostMapping("/submit")
    public String submitOrder(@RequestBody Order order) {
        order.setOrderId(OrderNumUtil.generateOrderNum(order.getUserId()));
        order.setOrderStatus(0);
        order.setCreateTime(new Date());
        int result = orderService.submitOrder(order);
        return result > 0 ? order.getOrderId() : "fail";
    }

    // 查询用户订单
    @GetMapping("/list/{userId}")
    public List<Order> getOrderList(@PathVariable Integer userId) {
        return orderService.getOrderByUserId(userId);
    }

    // 订单详情
    @GetMapping("/detail/{orderId}")
    public Order getOrderDetail(@PathVariable String orderId) {
        return orderService.getOrderByOrderId(orderId);
    }
}