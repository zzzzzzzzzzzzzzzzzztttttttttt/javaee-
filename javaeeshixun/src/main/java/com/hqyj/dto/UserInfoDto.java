package com.hqyj.dto;


import lombok.Data;

@Data
public class UserInfoDto {
    private Integer userId;
    private String userName;
    private String email;
    private String code;
    private String userPassword;
    private String confirmPassword;
    private String oldPassword;
}
