package com.hqyj.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hqyj.dto.UserInfoDto;
import com.hqyj.mapper.UserInfoMapper;
import com.hqyj.pojo.UserInfo;
import com.hqyj.service.UserInoService;
import com.hqyj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class UserInoServiceImpl implements UserInoService {
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String myEmail;
    //注入mapper
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public ResultVo sendEmail(UserInfoDto dto){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",dto.getEmail());
        UserInfo u= userInfoMapper.selectOne(queryWrapper);
        if (u == null){
            return ResultVo.fail("邮箱不存在",null);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(myEmail);
        //收件人
        message.setTo(dto.getEmail());
        //标题
        message.setSubject("hello");
        //正文
        message.setText("aaa");
        Random random = new Random();
        int code = random.nextInt(10000);
        message.setText("您的验证码是："+code);
        try{
                javaMailSender.send(message);
                //把验证码存入到redis中
            String key =dto.getEmail()+"code";
            //把code存到redis中的字符串类型，并设置失效时间是一分钟

            redisTemplate.opsForValue().set(key, code,50, TimeUnit.MINUTES);
            return ResultVo.success("发送成功",code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultVo.fail("发送失败",null);
    }
    @Override
    public ResultVo logInCode(UserInfoDto dto){
        //验证验证码是否失效
        String key =dto.getEmail()+"code";
        Object obj=redisTemplate.opsForValue().get(key);
        if (obj == null){
            return ResultVo.fail("验证码已失效,请重新发送",null);
        }
        //验证验证码是否正确
        if (!obj.toString().equals(dto.getCode())){
            return ResultVo.fail("验证码错误",null);
        }
        return ResultVo.success("验证码正确",null);
    }

    @Override
    public ResultVo login(UserInfoDto dto) {
        if(dto.getUserName() == null|| dto.getUserName().equals("")|| dto.getUserPassword() == null|| dto.getUserPassword().equals("")){
            return ResultVo.fail("用户名或密码不能为空",null);
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>().eq("user_name", dto.getUserName());
        UserInfo user = userInfoMapper.selectOne(queryWrapper);
        if (user == null){
            return ResultVo.fail("用户不存在",null);
        }
        if (user != null && user.getPassword().equals(dto.getUserPassword())){
            return ResultVo.success("登录成功",user);
        }
        return ResultVo.fail("密码错误",null);
    }

    @Override
    public ResultVo change(UserInfoDto dto) {
        if( dto.getUserPassword() == null|| dto.getUserPassword().equals("")|| dto.getConfirmPassword() == null|| dto.getConfirmPassword().equals("")){
            return ResultVo.fail("密码不能为空",null);
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>().eq("user_name", dto.getUserName());
        UserInfo user = userInfoMapper.selectOne(queryWrapper);

        if (user != null && user.getPassword().equals(dto.getOldPassword())){
            user.setPassword(dto.getUserPassword());
            if(user.getPassword().equals(dto.getOldPassword())){
                return ResultVo.fail("新旧密码不能相同",null);
            }
            if( !user.getPassword().equals(dto.getConfirmPassword())){
                return ResultVo.fail("前后两次密码不一致",null);
            }
            int i = userInfoMapper.update(user, queryWrapper);
            if (i > 0){
                return ResultVo.success("修改成功",null);
            }
        }else  if ( !user.getPassword().equals(dto.getOldPassword())){
            return ResultVo.fail("旧密码错误",null);
        }
        return null;
    }
}
