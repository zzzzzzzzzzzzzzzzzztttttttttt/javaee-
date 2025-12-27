package org.example.javaeeforll.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.javaeeforll.entity.User;
@Mapper
public interface UserMapper {
    // 用户注册
    @Insert("insert into t_user(username,password,real_name,phone,address,status,create_time) values(#{username},#{password},#{realName},#{phone},#{address},#{status},#{createTime})")
    int register(User user);

    // 用户登录

    User login(String username, String password);

    // 根据ID查询用户
    @Select("select * from t_user where user_id=#{userId}")
    User selectById(Integer userId);
}