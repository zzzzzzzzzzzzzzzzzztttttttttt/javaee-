package org.example.service;


import org.example.pojo.buy;

import java.util.List;

public interface BuyService {
    public List<buy> findById(Integer id);
}
