package org.example.Mappers;

import org.example.pojo.Customer;

public interface CustomerMapper {
    Customer findCustomerOrders1(int id);
    Customer findCustomerOrders2(int id);
}
