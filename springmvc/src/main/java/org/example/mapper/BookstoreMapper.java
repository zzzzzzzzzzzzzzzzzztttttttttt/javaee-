package org.example.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Bookstore;

import java.awt.print.Book;
import java.util.List;

@Mapper
public interface BookstoreMapper {
    Bookstore selectById(Integer id);
    int updateById(Bookstore bookstore);
    List<Bookstore> findAllBooks();
}
