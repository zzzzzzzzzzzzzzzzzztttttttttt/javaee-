package org.example.service;


import org.example.pojo.Bookstore;

import java.awt.print.Book;
import java.util.List;

public interface BookstoreService {
    public Bookstore selectById(Integer id);
    public int update(Bookstore bookstore);

    public List<Bookstore> findAllBooks();
}
