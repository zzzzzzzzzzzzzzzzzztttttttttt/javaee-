package org.example.service.impl;



import org.example.mapper.BookstoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.pojo.Bookstore;
import org.example.service.BookstoreService;

import java.awt.print.Book;
import java.util.List;

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
    @Override
    public List<Bookstore> findAllBooks(){
        System.out.println("findAllBooks");
        return bookstoreMapper.findAllBooks();
    }
}
