package org.example.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface BookstoreDao {
    @Delete("DELETE FROM bookstore WHERE id=#{id}; ")
    void remove(@Param("id") Integer id);
}
