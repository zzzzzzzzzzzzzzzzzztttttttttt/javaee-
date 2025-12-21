package com.hqyj.controller;

import com.hqyj.dto.UserInfoDto;
import com.hqyj.service.UserInoService;
import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    //注入
    @Autowired
    private UserInoService userInoService;
    @PostMapping("/sendEmail")
    public ResultVo sendEmail(@RequestBody UserInfoDto dto){
        return userInoService.sendEmail(dto);
    }
    @PostMapping("/loginCode")
    public ResultVo loginCode(@RequestBody UserInfoDto dto){
        return userInoService.logInCode(dto);
    }
    @PostMapping("/login")
    public ResultVo login(@RequestBody UserInfoDto dto){
        System.out.println(dto);
        return userInoService.login(dto);
    }
    @PostMapping("/change")
    public ResultVo change(@RequestBody UserInfoDto dto){
        return userInoService.change(dto);
    }


}
