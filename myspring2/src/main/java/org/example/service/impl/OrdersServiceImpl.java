package org.example.service.impl;

import org.example.mapper.OrdersMapper;
import org.example.pojo.Bookstore;
import org.example.pojo.Orders;
import org.example.service.BookstoreService;
import org.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private BookstoreService  bookstoreService;
    @Override
    @Transactional
    public void byBook(Integer customerId, Integer bookId, Integer nums){
        Orders orders=this.findById(bookId, customerId);
        Date nowTime=new Date();
        Bookstore bookstore=bookstoreService.selectById(bookId);
        System.out.println(orders);
        if(orders==null){
            if(bookstore.getAmount()>=nums){
                ordersMapper.add(customerId,bookId,nums,nowTime);
                bookstore.setAmount(bookstore.getAmount()-nums);
                bookstoreService.update(bookstore);
            }else{
                System.out.println("库存不足");
            }
        }else{
            if(bookstore.getAmount()>=nums){
                ordersMapper.update(customerId,bookId,nums+orders.getNums(),nowTime);
                bookstore.setAmount(bookstore.getAmount()-nums);
                bookstoreService.update(bookstore);
            }else{
                System.out.println("库存不足");
            }
        }
    }
    @Override
     public List<Orders> findCustomerBybook(Integer id){
        List<Orders> o= ordersMapper.findCustomerBybook(id);
        return o;
    }
    @Override
    public Orders findById(Integer bookId,Integer customerId){
        return ordersMapper.findById(bookId,customerId);
    }
}
