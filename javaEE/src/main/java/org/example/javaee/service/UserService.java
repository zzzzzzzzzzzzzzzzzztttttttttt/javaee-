package org.example.javaee.service;


import org.example.javaee.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Integer id);


    boolean updateMoney(String username, Double money);
}