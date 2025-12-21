package org.example.service.impl;

import org.example.mapper.BuyMapper;
import org.example.pojo.buy;
import org.example.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BuyServiceIImpl implements BuyService {
    @Autowired
    private BuyMapper buyService;
    @Override

    public List<buy> findById(Integer id){
        List<buy> b=buyService.b(id);
        return b;
    }
}
