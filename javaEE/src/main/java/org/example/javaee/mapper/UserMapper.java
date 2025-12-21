package org.example.javaee.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.javaee.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据ID查询用户
    User selectById(Integer id);

    // 查询所有用户
//    @Select("SELECT *FROM user")
    List<User> selectAll();

    // 新增用户
    int insert(User user);

    // 更新用户
    int update(User user);

    // 根据ID删除用户
    int deleteById(Integer id);

    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    int updateMoney(String username, Double money);
}