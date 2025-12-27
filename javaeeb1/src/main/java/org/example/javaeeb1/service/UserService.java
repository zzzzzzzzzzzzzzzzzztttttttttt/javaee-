package org.example.javaeeb1.service;

import org.example.javaeeb1.pojo.User;
import org.example.javaeeb1.pojo.UserDto;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:04:11
 */
public interface UserService  {

    User login(String username);

    Boolean register(UserDto userDto);

    User findByusername(String username);
}
