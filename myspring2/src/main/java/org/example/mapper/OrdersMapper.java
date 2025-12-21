package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Orders;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrdersMapper {
    void add(
            @Param("customerId") Integer customerId, // 对应 #{customerId}
            @Param("bookId") Integer bookId,         // 对应 #{bookId}
            @Param("nums") Integer nums   ,         // 对应 #{count}
            @Param("nowTime") Date nowTime             // 对应 #{count}
    );
    void update(
            @Param("customerId") Integer customerId, // 对应 #{customerId}
            @Param("bookId") Integer bookId,         // 对应 #{bookId}
            @Param("nums") Integer nums ,          // 对应 #{count}
            @Param("nowTime") Date nowTime            // 对应 #{count}
    );

    List<Orders> findCustomerBybook(Integer id);
    Orders findById( // 对应 #{customerId}
                     @Param("bookId") Integer bookId,
                       @Param("customerId") Integer customerId
    );
}
