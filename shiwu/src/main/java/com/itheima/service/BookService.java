package com.itheima.service;


import com.itheima.dao.BookDao;
import org.springframework.transaction.annotation.Transactional;

public class BookService {
    private BookDao bookDao;
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    public void deleteByIdxml(int id) {
        bookDao.deleteById(id);
        int i=1/0;
    }




    @Transactional
    public void deleteByIdxmlannotation(int id) {
        bookDao.deleteById(id);
        int i=1/0;
    }

}