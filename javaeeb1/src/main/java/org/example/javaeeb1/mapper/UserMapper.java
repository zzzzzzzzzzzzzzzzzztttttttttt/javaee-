package org.example.javaeeb1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.example.javaeeb1.pojo.User;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rock
 * @since 2025-12-26 08:04:11
 */
@Mapper
public interface UserMapper  {

    User findByUsername(String username);
    @Insert("insert into user(username,password) values(#{username},#{password})")
    void insert(User u);
}
