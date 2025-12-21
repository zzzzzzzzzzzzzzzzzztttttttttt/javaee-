package org.example.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Customer;
@Mapper
public interface CustomerMapper {
    Customer findCustomerOrders1(int id);

}
