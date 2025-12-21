package org.example.javaeeforll.service.impl;


import jakarta.annotation.Resource;
import org.example.javaeeforll.entity.User;
import org.example.javaeeforll.mapper.UserMapper;
import org.example.javaeeforll.service.UserService;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public User getById(Integer userId) {
        return userMapper.selectById(userId);
    }
}