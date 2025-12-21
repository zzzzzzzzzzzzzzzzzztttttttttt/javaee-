package org.example.service;

import org.example.pojo.Bookstore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface BookstoreService {
    public Bookstore  selectById(Integer id);
    public int update(Bookstore bookstore);
}
