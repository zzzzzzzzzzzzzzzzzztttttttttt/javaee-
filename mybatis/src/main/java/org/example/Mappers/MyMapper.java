package org.example.Mappers;

import org.example.pojo.Book;

public interface MyMapper {
    Book find(int id);
    int add(Book book);
    int update(Book book);
    int delete(int id);
}
