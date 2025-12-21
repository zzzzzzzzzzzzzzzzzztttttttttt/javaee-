package org.example.service.impl;


import org.example.mapper.BookstoreMapper;
import org.example.pojo.Bookstore;
import org.example.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookstoreServiceImpl implements BookstoreService {
    @Autowired
    private BookstoreMapper bookstoreMapper;
    @Override
    public Bookstore selectById(Integer id){
        return bookstoreMapper.selectById(id);
    }
    @Override
    public int update(Bookstore bookstore){
        return bookstoreMapper.updateById(bookstore);
    }
}
