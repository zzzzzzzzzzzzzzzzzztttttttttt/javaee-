package org.example.javaeeforll.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String address;
    private Integer status; // 1-正常 0-禁用
    private Date createTime;
}