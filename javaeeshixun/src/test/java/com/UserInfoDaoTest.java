package com;

import com.hqyj.MyApp;
import com.hqyj.mapper.UserInfoMapper;
import com.hqyj.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import java.util.List;

@RunWith(SpringRunner.class)//spring测试的运行器注解
@SpringBootTest(classes = MyApp.class) //springboot测试类注解
public class UserInfoDaoTest {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Test
    public void query(){
        List<UserInfo> userInfoList = userInfoMapper.selectList(null);
        System.out.println("查询结果：");
        for (UserInfo userInfo : userInfoList) {
            System.out.println(userInfo);
        }
    }

}