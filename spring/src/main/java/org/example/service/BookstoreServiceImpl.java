package org.example.service;

import org.example.dao.BookstoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookstoreServiceImpl implements BookstoreService{
    @Autowired
    BookstoreDao bookstoreDao;

    @Override
    public void remove(Integer id) {
        bookstoreDao.remove(2);
    }
}
