package org.example.service.impl;


import org.example.mapper.BuyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.pojo.buy;
import org.example.service.BuyService;

import java.util.List;
@Service
public class BuyServiceIImpl implements BuyService {
    @Autowired
    private BuyMapper buyMapper;
    @Override

    public List<buy> findById(Integer id){
        List<buy> b=buyMapper.b(id);
        return b;
    }
}
