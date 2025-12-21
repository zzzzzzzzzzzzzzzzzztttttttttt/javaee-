package org.example.javaee.service.impl;


import org.example.javaee.entity.User;
import org.example.javaee.mapper.UserMapper;
import org.example.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public User getUserByUsername(String username){
        return userMapper.getByUsername(username);
    }
    @Override
    public boolean updateMoney(String username, Double money){
        return userMapper.updateMoney(username,money)>0;
    }

}