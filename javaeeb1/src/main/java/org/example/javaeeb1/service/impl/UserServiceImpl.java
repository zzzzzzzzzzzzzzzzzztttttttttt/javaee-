package org.example.javaeeb1.service.impl;

import jakarta.annotation.Resource;
import org.example.javaeeb1.pojo.User;
import org.example.javaeeb1.mapper.UserMapper;
import org.example.javaeeb1.pojo.UserDto;
import org.example.javaeeb1.service.UserService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:04:11
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(String userName) {
        return userMapper.findByUsername(userName);
    }

    @Override
    public Boolean register(UserDto userDto) {
        User user = userMapper.findByUsername(userDto.getUsername());
        if (user==null){
            User u = new User();
            u.setUsername(userDto.getUsername());
            u.setPassword(userDto.getPassword());
            userMapper.insert(u);
            return true;
        }
        return false;
    }

    @Override
    public User findByusername(String username) {
        return userMapper.findByUsername(username);
    }

}
