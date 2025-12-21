package org.example.Mappers;

import org.example.pojo.Book;

import java.util.List;

public interface SqlMapper {
    List<Book>  find(Book book);
    List<Book>  findByPress(List<String>  press);
}
