package org.example.service;

import org.example.pojo.Orders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface OrdersService {
    public void byBook(Integer customerId, Integer bookId, Integer nums);
    public List<Orders> findCustomerBybook(Integer id);
    public Orders findById(Integer bookId,Integer customerId);
}
