package org.example.javaeeb1.service;

import org.example.javaeeb1.common.Result;
import org.example.javaeeb1.pojo.Order;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:03:40
 */
public interface OrderService {

    Result getAll(String nowUser);

    Result add(String nowUser);
}
