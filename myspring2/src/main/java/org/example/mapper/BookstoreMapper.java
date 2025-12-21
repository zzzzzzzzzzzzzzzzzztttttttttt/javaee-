package org.example.mapper;

import org.example.pojo.Bookstore;

public interface BookstoreMapper {
    Bookstore selectById(Integer id);
    int updateById(Bookstore bookstore);
}
