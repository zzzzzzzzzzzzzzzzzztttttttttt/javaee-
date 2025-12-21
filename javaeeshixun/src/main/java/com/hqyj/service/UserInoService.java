package com.hqyj.service;

import com.hqyj.dto.UserInfoDto;
import com.hqyj.vo.ResultVo;

public interface UserInoService {
    ResultVo sendEmail(UserInfoDto dto);
    //邮箱验证码登录
    ResultVo logInCode(UserInfoDto dto);

    ResultVo login(UserInfoDto dto);

    ResultVo change(UserInfoDto dto);
}
