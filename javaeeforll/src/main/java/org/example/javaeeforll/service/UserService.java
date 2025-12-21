package org.example.javaeeforll.service;


import org.example.javaeeforll.entity.User;

public interface UserService {
    int register(User user);
    User login(String username, String password);
    User getById(Integer userId);
}